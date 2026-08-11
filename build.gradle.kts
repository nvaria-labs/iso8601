import io.github.simonhauck.release.version.api.Version

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

group = "ch.nvaria.impulse-platform"

val versionDetails: groovy.lang.Closure<com.palantir.gradle.gitversion.VersionDetails> by extra
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


spdxSbom {
    onlyUseLocalLicenses.set(true)
    targets {
        create("release") {
            // Everything on the runtime classpath (test deps stay out). Note this
            // includes the ANTLR *tool* jar + ST4/treelayout — the antlr plugin puts
            // it on 'api', so it is a real runtime-classpath entry here.
            configurations.set(listOf("runtimeClasspath"))

            scm {
                tool.set("git")
                uri.set("https://gitea.nvaria.cloud/nvaria/iso8601_lib")
                revision.set(details.gitHash)
            }

            document {
                name.set("iso8601lib")
                namespace.set("https://nvaria.ch/spdx/iso8601lib/${project.version}")
                creator.set("Organization: Nvaria GmbH")
                packageSupplier.set("Organization: Nvaria GmbH")
            }
        }
    }
}

// Two things the plugin cannot express, patched into the finished document:
//
//  1. the license of the root (project) package — hardcoded to NOASSERTION, no DSL
//     and no task-extension hook for it (dependency licenses come from their POMs
//     and are already correct);
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
            groupId = "ch.nvaria.impulse-platform"
            artifactId = "iso8601lib"

            from(components["java"])
            artifact(sbomFile) {
                builtBy(tasks.named("spdxSbomForRelease"))
                classifier = "sbom"
                extension = "spdx.json"
            }
            pom {
                name.set("iso8601lib")
                description.set("A Library to read iso8601 strings")
                url.set("")

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
                    }
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


val mockitoAgent = configurations.create("mockitoAgent")

sourceSets.configureEach {
    val generateGrammarSource = tasks.named(getTaskName("generate", "GrammarSource"))
    java.srcDir(generateGrammarSource.map { files() })
}

dependencies {
    antlr(libs.antlr.v4)
    implementation(libs.antlr.v4.runtime)

    mockitoAgent(libs.mockito.core) { isTransitive = false }
    testImplementation(libs.hamcrest)
    testImplementation(libs.hamcrest.date)

    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)
}

tasks.named<Test>("test") {

    jvmArgs("-XX:+EnableDynamicAgentLoading",  "-XX:+UnlockExperimentalVMOptions","-XX:+EnableJVMCI", "-javaagent:${mockitoAgent.asPath}")
    useJUnitPlatform()
}



tasks.generateGrammarSource {
    outputDirectory = file(layout.projectDirectory.dir("src/main/java/ch/nvaria/generated"))
    maxHeapSize = "64m"
    arguments = arguments + listOf("-visitor", "-long-messages", "-package" ,"ch.nvaria.generated")
}

tasks.named("test").configure { dependsOn("jar") }

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
            exclude("ch/nvaria/generated/*")
        }
    )
}
tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:unchecked")
    options.isDeprecation = true
}

tasks.withType<Javadoc>().configureEach {
    exclude("ch/nvaria/generated/**")
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:all,-missing", "-quiet")
}

tasks.named<Jar>("sourcesJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}