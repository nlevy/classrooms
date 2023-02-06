plugins {
    kotlin("jvm") version "1.7.10"
}

group = "com.nirlevy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":genetic-algorithm"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
