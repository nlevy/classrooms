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
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.4")
    implementation("org.apache.logging.log4j:log4j-api:2.14.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
}

tasks.test {
    useJUnitPlatform()
}
