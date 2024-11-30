plugins {
    `java-library`
    idea
    id("com.github.ben-manes.versions") version "0.51.0"
    id("org.barfuin.gradle.jacocolog") version "3.1.0"
    `maven-publish`
    jacoco
    antlr
}

group = "ch.tm.impulse-platform"
version = "1.0"


java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
}

repositories {
    mavenCentral()
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

    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
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

