import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val koinVersion = "1.0.2"
val ktorVersion = "1.1.3"

plugins {
    application
    kotlin("jvm") version "1.3.11"
}

group = "com.tomasz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    // Koin for Kotlin apps
    compile("org.koin:koin-core:$koinVersion")
    // Ktor
    compile("io.ktor:ktor-server-core:$ktorVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    // Logging provider
    compile("ch.qos.logback:logback-classic:1.2.3")
    // Testing
    testCompile("org.koin:koin-test:1.0.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}