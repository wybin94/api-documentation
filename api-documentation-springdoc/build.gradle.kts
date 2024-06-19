import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.1.11"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springdoc.openapi-gradle-plugin") version "1.8.0"
}

group = "api-documentation-springdoc"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<BootBuildImage>("bootBuildImage") {
    builder = "paketobuildpacks/builder-jammy-base:latest"
}

//tasks.test {
//    useJUnitPlatform()
//}
tasks.named<Test>("test"){
    useJUnitPlatform()
}

openApi {
    apiDocsUrl.set("http://localhost:8080/v3/api-docs.yaml")
    outputDir.set(file("$buildDir/docs"))
    outputFileName.set("swagger.yaml")
    waitTimeInSeconds.set(10)
//    groupedApiMappings.set(mapOf(
//        "http://localhost:8080/v3/api-docs/members" to "swagger-members.json",
//        "http://localhost:8080/v3/api-docs/pets" to "swagger-pets.json"
//    ))
//    groupedApiMappings.set(["https://localhost:8080/v3/api-docs/groupA" to "swagger-groupA.json",
//        "https://localhost:8080/v3/api-docs/groupB" to "swagger-groupB.json"])
//    customBootRun {
//        args.set(["--spring.profiles.active=special"])
//    }
}