import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":user:api"))
    implementation(project(":user:domain"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.data:spring-data-mongodb")
    implementation("io.github.oshai:kotlin-logging-jvm:4.0.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

//configurations {
//    all {
//        exclude(group = "io.klogging:slf4j-klogging:0.3.3", module = "spring-boot-starter-logging")
//    }
//}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}
