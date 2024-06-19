plugins {
    java
    id("org.springframework.boot") version "3.1.11"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

val asciidoctorExtensions by configurations.creating // 2

group = "api-documentation"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // src/docs/asciidoc/*.adoc 를 변환
    testImplementation("org.springframework.restdocs:spring-restdocs-asciidoctor")
    asciidoctorExtensions("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootBuildImage {
    builder.set("paketobuildpacks/builder-jammy-base:latest")
}

tasks.test {
    outputs.dir(project.extra["snippetsDir"]!!)
}

tasks.asciidoctor {
    inputs.dir(project.extra["snippetsDir"]!!)
    configurations(asciidoctorExtensions.name)
    // source를 지정하면 특정 adoc만 HTML로 만든다.
    sources {
        include("**/index.adoc")
    }
    // 경로를 baseDir로 맞춰준다!
    baseDirFollowsSourceFile()
    dependsOn(tasks.test)
}
