plugins {
    java
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.0"
}

group = "me.gallowsdove"
version = "MODIFIED"

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release = 25
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
    compileOnly("com.github.slimefun:Slimefun:4.9-UNOFFICIAL")
    implementation("com.github.Mooy1:InfinityLib:1.3.9")
    compileOnly("com.github.Slimefun.dough:dough-api:cb22e71335")
}

tasks.shadowJar {
    archiveClassifier = ""
    relocate("io.github.mooy1.infinitylib", "me.gallowsdove.foxymachines.infinitylib")
    relocate("io.github.bakedlibs.dough", "me.gallowsdove.foxymachines.libraries.dough")
    exclude("META-INF/**")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.processResources {
    filesMatching("plugin.yml") {
        expand(mapOf("project" to project))
    }
}
