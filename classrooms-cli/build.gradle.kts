plugins {
    kotlin("jvm") version "1.7.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv:2.14.2")
    implementation(project(":classrooms-engine"))
    implementation(project(":genetic-algorithm"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
