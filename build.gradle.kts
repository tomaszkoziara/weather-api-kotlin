import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = "1.3.20"
val koinVersion = "1.0.2"
val ktorVersion = "1.1.3"
val fuelVersion = "2.0.1"

plugins {
    java
    idea
    application
    kotlin("jvm") version "1.3.20"
}

group = "com.tomasz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

dependencies {
    compile("org.jetbrains.kotlin:kotlin-reflect:1.3.20")
    compile("org.jetbrains.kotlin:kotlin-serialization:1.3.20")
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
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.0")
    testCompile("org.koin:koin-test:1.0.2")
    testCompile("io.mockk:mockk:1.9.1")
    // Joda Time
    compile("joda-time:joda-time:2.10.1")
    // Fuel
    compile("com.github.kittinunf.fuel:fuel:$fuelVersion")
    compile("com.github.kittinunf.fuel:fuel-coroutines:$fuelVersion")
    // Gson
    compile("com.google.code.gson:gson:2.8.5")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.getByName<Test>("test") {
    maxHeapSize="256m"
    useJUnitPlatform()
}

application {
    mainClassName = "MainKt"
}