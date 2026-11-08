import io.github.simonhauck.release.version.api.Version

buildscript {
    repositories { gradlePluginPortal() }
    // palantir git-version needs Guava at runtime but doesn't declare it
    dependencies { classpath(libs.guava) }
}

plugins {
    `java-library`
    idea
    alias(libs.plugins.versions)
    alias(libs.plugins.jacocolog)
    alias(libs.plugins.release)
    alias(libs.plugins.git.version)
    alias(libs.plugins.spdx.sbom)
    `maven-publish`
    jacoco
    antlr
}

group = "ch.nvaria"

@Suppress("UNCHECKED_CAST")
val versionDetails = extra["versionDetails"] as groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails>
val details = versionDetails()
val versionFile = layout.settingsDirectory.file("version.properties").asFile
version = Version.fromPropertiesFile(versionFile)

if(details.branchName != null && details.branchName.contains("/")){
    val branchname = details.branchName.replace("/", "-")
    val ref = "-"+branchname+"-"
    val myVersion = Version.fromPropertiesFile(versionFile).split("-")
    version =  myVersion[0] + ref + myVersion[1]
}


release {
    checkForPreReleaseVersions.set(false)
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withSourcesJar()
    withJavadocJar()
}

repositories {
    //mavenLocal()
    //mavenCentral()

    maven {
        name = "maven-public"
        url = uri("https://nexus.nvaria.cloud/repository/maven-public/")
        credentials {
            username = System.getenv("NEXUS_USER")
            password = System.getenv("NEXUS_PW")
        }
    }
    maven {
        name = "maven-release"
        url = uri(System.getenv("NEXUS_MVN_RELEASE"))
        credentials {
            username = System.getenv("NEXUS_USER")
            password = System.getenv("NEXUS_PW")
        }
    }
    maven {
        url = uri(System.getenv("NEXUS_MVN_SNAPSHOTS"))
        credentials {
            username = System.getenv("NEXUS_USER")
            password = System.getenv("NEXUS_PW")
        }
    }
}


// Single source of truth for our own license: both the POM and the SBOM patch below
// read these, so the published metadata cannot drift apart. SPDX identifier form —
// it is what the SBOM requires, and it is valid as a POM license name too.
val licenseId = "Apache-2.0"
val licenseUrl = "https://www.apache.org/licenses/LICENSE-2.0"
val copyrightHolder = "Nvaria GmbH"

// Public home of the project. Gitea is the source of truth; GitHub is its read-only mirror.
val projectUrl = "https://github.com/nvaria-labs/iso8601"


spdxSbom {
    onlyUseLocalLicenses.set(true)
    targets {
        create("release") {
            // Everything on the runtime classpath (test deps stay out).
            configurations.set(listOf("runtimeClasspath"))

            scm {
                tool.set("git")
                uri.set(projectUrl)
                revision.set(details.gitHashFull)
            }

            document {
                name.set("iso8601")
                namespace.set("https://nvaria.ch/spdx/iso8601/${project.version}")
                creator.set("Organization: Nvaria GmbH")
                packageSupplier.set("Organization: Nvaria GmbH")
            }
        }
    }
}

// Two things the plugin cannot express, patched into the finished document:
//
//  1. the license and download location of the root (project) package — hardcoded to
//     NOASSERTION, no DSL and no task-extension hook for it (dependency licenses come
//     from their POMs and are already correct);
//  2. the download locations — everything resolves through the nexus mirror, so the
//     plugin records internal, auth-required URLs that no consumer can fetch, and
//     stamps the mirror host into every purl as a ?repository_url= qualifier.
//
// Deliberately NOT done via the plugin's taskExtension.mapRepoUri: that property is
// @Internal, so changing the mapping would not invalidate the task and the SBOM would
// silently go stale. Mutating the declared @OutputFile here keeps caching honest.
tasks.named("spdxSbomForRelease") {
    doLast {
        val json = layout.buildDirectory.file("spdx/release.spdx.json").get().asFile
        @Suppress("UNCHECKED_CAST")
        val doc = groovy.json.JsonSlurper().parse(json) as MutableMap<String, Any>

        // The root package is whatever SPDXRef-DOCUMENT DESCRIBES; don't match on name.
        @Suppress("UNCHECKED_CAST")
        val relationships = doc["relationships"] as List<Map<String, Any>>
        val rootId = relationships.single {
            it["spdxElementId"] == "SPDXRef-DOCUMENT" && it["relationshipType"] == "DESCRIBES"
        }["relatedSpdxElement"]

        @Suppress("UNCHECKED_CAST")
        val packages = doc["packages"] as List<MutableMap<String, Any>>
        val root = packages.single { it["SPDXID"] == rootId }
        root["licenseDeclared"] = licenseId
        root["licenseConcluded"] = licenseId
        root["copyrightText"] = "Copyright $copyrightHolder"
        root["downloadLocation"] = "git+$projectUrl.git@${details.gitHashFull}"

        // Our own artifacts genuinely only live in nexus — leave those pointing there.
        // Everything else is a Central artifact the mirror merely proxies.
        val mirror = "https://nexus.nvaria.cloud/repository/maven-public/"
        val central = "https://repo1.maven.org/maven2/"
        packages.filterNot { it["name"].toString().startsWith("ch.nvaria") }.forEach { pkg ->
            val location = pkg["downloadLocation"] as? String
            if (location != null && location.startsWith(mirror)) {
                pkg["downloadLocation"] = central + location.removePrefix(mirror)
            }
            @Suppress("UNCHECKED_CAST")
            val refs = pkg["externalRefs"] as? List<MutableMap<String, Any>> ?: emptyList()
            refs.forEach { ref ->
                val purl = ref["referenceLocator"] as? String ?: return@forEach
                // Drop only the mirror qualifier; keep any others (classifier, type, …).
                val kept = purl.substringAfter('?', "").split("&")
                    .filterNot { it.startsWith("repository_url=") }
                ref["referenceLocator"] = purl.substringBefore('?') +
                        if (kept.any { it.isNotEmpty() }) "?" + kept.filter { it.isNotEmpty() }.joinToString("&") else ""
            }
        }

        json.writeText(groovy.json.JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(doc)))
    }
}

val sbomFile = layout.buildDirectory.file("spdx/release.spdx.json")

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "iso8601"

            from(components["java"])
            artifact(sbomFile) {
                builtBy(tasks.named("spdxSbomForRelease"))
                classifier = "sbom"
                extension = "spdx.json"
            }
            pom {
                name.set("iso8601")
                description.set("Parser for ISO 8601-1 dates, times, durations, time intervals and recurring time intervals")
                url.set(projectUrl)
                inceptionYear.set("2024")

                organization {
                    name.set(copyrightHolder)
                    url.set("https://nvaria.ch")
                }

                licenses {
                    license {
                        name.set(licenseId)
                        url.set(licenseUrl)
                    }
                }
                developers {
                    developer {
                        id.set("tm")
                        name.set("Thomas Müllerleile")
                        email.set("thomas.muellerleile@nvaria.ch")
                        organization.set(copyrightHolder)
                    }
                }
                scm {
                    url.set(projectUrl)
                    connection.set("scm:git:$projectUrl.git")
                    developerConnection.set("scm:git:$projectUrl.git")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("$projectUrl/issues")
                }
            }
        }
        repositories {
            maven {
                name = "maven-snapshots"
                url = uri(System.getenv("NEXUS_MVN_SNAPSHOTS"))
                credentials {
                    username = System.getenv("NEXUS_USER")
                    password = System.getenv("NEXUS_PW")
                }
            }
        }
        repositories {
            maven {
                name = "maven-release"
                url = uri(System.getenv("NEXUS_MVN_RELEASE"))
                credentials {
                    username = System.getenv("NEXUS_USER")
                    password = System.getenv("NEXUS_PW")
                }
            }
        }
    }
}


sourceSets.configureEach {
    val generateGrammarSource = tasks.named(getTaskName("generate", "GrammarSource"))
    java.srcDir(generateGrammarSource.map { files() })
}

// The antlr plugin makes 'api' extend 'antlr', which would publish the ANTLR *tool*
// (plus ST4, antlr3, icu4j, ...) as a compile dependency. Consumers only need the runtime.
configurations.api {
    setExtendsFrom(extendsFrom.filterNot { it.name == "antlr" })
}

dependencies {
    antlr(libs.antlr.v4)
    implementation(libs.antlr.v4.runtime)

    testImplementation(libs.hamcrest)
    testImplementation(libs.hamcrest.date)

    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}



tasks.generateGrammarSource {
    maxHeapSize = "64m"
    packageName = "ch.nvaria.iso8601.internal.parser"
    arguments = arguments + listOf("-visitor", "-long-messages")
}


tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
    dependsOn(tasks.test) // tests are required to run before generating the report
    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude("ch/nvaria/iso8601/internal/parser/*")
        }
    )
}
tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:unchecked")
    options.isDeprecation = true
}

// Document only the packages exported by module-info. The excluded internals are
// patched into the module from the compiled classes so the API sources still resolve.
tasks.named<Javadoc>("javadoc") {
    val mainClasses = sourceSets.main.get().output.classesDirs
    inputs.files(mainClasses)
    exclude("ch/nvaria/iso8601/internal/**")
    (options as StandardJavadocDocletOptions).addStringOption(
        "-patch-module", "ch.nvaria.iso8601=" + mainClasses.asPath)
}

tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all,-missing", "-quiet")
}

// LICENSE and NOTICE travel with every published jar, as the Apache License asks.
tasks.withType<Jar>().configureEach {
    from(layout.projectDirectory) {
        include("LICENSE", "NOTICE")
        into("META-INF")
    }
}

tasks.named<Jar>("sourcesJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}