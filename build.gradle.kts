group = "org.juol.aoc"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "2.2.21"
}

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("org.jgrapht:jgrapht-core:1.5.2")
    implementation("org.jgrapht:jgrapht-ext:1.5.2")
    implementation("org.locationtech.jts:jts-core:1.20.0")

    testImplementation(kotlin("test"))
}
