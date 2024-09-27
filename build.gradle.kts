/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    api("com.google.code.gson:gson:2.10.1")
    // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    api("io.rest-assured:rest-assured:5.5.0")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    api("com.fasterxml.jackson.core:jackson-core:2.17.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    api("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    // https://mvnrepository.com/artifact/io.qameta.allure/allure-testng
    api("io.qameta.allure:allure-testng:2.29.0")

    // https://mvnrepository.com/artifact/org.testng/testng
    testImplementation("org.testng:testng:7.10.2")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

group = "API"
version = "1.0-SNAPSHOT"
description = "api"
java.sourceCompatibility = JavaVersion.VERSION_11

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.named<Test>("test") {
    useTestNG()
}
