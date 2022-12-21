plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.6.7"
    id("io.micronaut.test-resources") version "3.6.7"
}

version = "0.1"
group = "com.ems"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut.openapi:micronaut-openapi")
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.security:micronaut-security-annotations")
    kapt("io.micronaut.security:micronaut-security")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut.beanvalidation:micronaut-hibernate-validator")
    implementation("io.micronaut.cache:micronaut-cache-caffeine")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.security:micronaut-security")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.security:micronaut-security-oauth2")
    implementation("io.micronaut.sql:micronaut-hibernate-jpa")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.micronaut.xml:micronaut-jackson-xml")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("javax.inject:javax.inject:1")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.postgresql:postgresql")
    implementation("io.micronaut:micronaut-validation")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")

    testAnnotationProcessor("io.micronaut:micronaut-inject-java")
    implementation("org.slf4j:slf4j-api:1.6.1")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

}


application {
    mainClass.set("com.ems.be.Application")
}

allOpen {
    annotation("io.micronaut.aop.Around")
    annotation("io.micronaut.http.annotation.Controller")
    annotation("javax.inject.Singleton")
}

java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

kapt {
    arguments {
        arg("micronaut.processing.incremental", true)
        arg("micronaut.processing.annotations", "com.ems.be.*")
    }
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.ems.be.*")
    }
}



