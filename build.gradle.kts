plugins {
    java
    id("io.qameta.allure") version "2.9.6"
}

group = "API"
version = "1.0-SNAPSHOT"
description = "api"

repositories {
    mavenCentral()
}
dependencies {
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")
    // https://mvnrepository.com/artifact/io.rest-assured/rest-assured
    implementation("io.rest-assured:rest-assured:5.5.0")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    // https://mvnrepository.com/artifact/io.qameta.allure/allure-testng
    implementation("io.qameta.allure:allure-testng:2.29.0")

    // https://mvnrepository.com/artifact/org.testng/testng
    testImplementation("org.testng:testng:7.10.2")
    // https://mvnrepository.com/artifact/io.qameta.allure/allure-rest-assured
    implementation("io.qameta.allure:allure-rest-assured:2.29.0")

    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    implementation("org.projectlombok:lombok:1.18.34")
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
}

tasks.test {
    useTestNG()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allure {
    version.set("2.20.0")
    adapter.autoconfigure.set(true)
}

tasks.register("cleanAllure") {
    doLast {
        delete("build/allure-results", "build/allure-report")
    }
}
