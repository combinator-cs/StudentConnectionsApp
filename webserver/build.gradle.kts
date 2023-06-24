import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.2"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    id("com.google.cloud.tools.jib") version "3.3.1"
}

group = "com.proj"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":user:domain"))
    implementation(project(":user:routing"))

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-csv") { isTransitive = false }
    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.module:jackson-module-mrbean") { isTransitive = false }
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0") { isTransitive = false }
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0") { isTransitive = false }
    api("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.mongodb:mongodb-driver-reactivestreams")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.data:spring-data-mongodb")
    implementation("io.github.oshai:kotlin-logging-jvm:4.0.0")

}

//configurations {
//    all {
//        exclude(group = "io.klogging:slf4j-klogging:0.3.3", module = "spring-boot-starter-logging")
//    }
//}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

task("runLocalWebApplication", JavaExec::class) {
    classpath =
            sourceSets.main.get().runtimeClasspath //+ files(sourceSets.test.get().resources.srcDirs)
    mainClass.set("com.proj.services.webserver.ServicesApplication")

    // enable access logs
    systemProperty("reactor.netty.http.server.accessLogEnabled", "true")

    val httpsPort = ""

    args = listOf(
            "--debug",
            "--spring.config.location=src/main/resources/application.properties",
            "--server.port=$httpsPort"
    )
}
