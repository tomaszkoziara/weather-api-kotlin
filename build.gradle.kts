import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val koinVersion = "1.0.2"
val ktorVersion = "1.1.3"

plugins {
    java
    idea
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
    compile("org.jetbrains.kotlin:kotlin-reflect:1.3.10")
    compile(kotlin("stdlib-jdk8"))
    // Koin for Kotlin apps
    compile("org.koin:koin-core:$koinVersion")
    compile("org.koin:koin-ktor:$koinVersion")
    // Ktor
    compile("io.ktor:ktor-server-core:$ktorVersion")
    compile("io.ktor:ktor-server-netty:$ktorVersion")
    // Logging provider
    compile("org.slf4j:slf4j-log4j12:1.7.25")
    // Testing
    testCompile("org.koin:koin-test:1.0.2")
    // Joda Time
    compile("joda-time:joda-time:2.10.1")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}