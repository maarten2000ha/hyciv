plugins {
    java
    id("org.jetbrains.kotlin.jvm") version "2.3.0"
    id("com.gradleup.shadow") version "8.3.0"
}

group = "me.maarten2000ha.hyciv"
version = "1.0.0"

repositories {
    mavenCentral()
    // Official Hytale Maven repository
    maven {
        name = "hytale-release"
        url = uri("https://maven.hytale.com/release")
    }
    maven {
        name = "hytale-pre-release"
        url = uri("https://maven.hytale.com/pre-release")
    }
}

dependencies {
    // Hytale Server API from official Maven repository
    compileOnly("com.hypixel.hytale:Server:+")
    // JSR305 annotations (@Nonnull, @Nullable)
    compileOnly("com.google.code.findbugs:jsr305:3.0.2")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation(kotlin("stdlib"))
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveClassifier.set("")
    // Exclude server classes from the final JAR
    dependencies {
        exclude(dependency("com.hypixel:.*"))
    }
}

// Disable the default jar task to avoid conflicts with shadowJar
tasks.named<Jar>("jar") {
    enabled = false
}

tasks.named("build") {
    dependsOn("shadowJar")
}

// Deploy plugin JAR to server mods folder
tasks.register<Copy>("deployToServer") {
    // Using 'from shadowJar' automatically adds task dependency and proper input tracking
    from(tasks.named("shadowJar"))
    into("server/mods")
    doLast {
        println("Deployed to server/mods/")
    }
}

// Watch for changes and auto-rebuild (useful during development)
tasks.register("watch") {
    doLast {
        println("Watching for changes... Press Ctrl+C to stop.")
        println("Run 'gradle build --continuous' for auto-rebuild on file changes.")
    }
}
