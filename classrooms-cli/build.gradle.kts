plugins {
    kotlin("jvm") version "1.7.10"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.apache.commons:commons-csv:1.9.0")
    implementation(project(":classrooms-engine"))
    implementation(project(":genetic-algorithm"))
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
