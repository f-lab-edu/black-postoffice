import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("org.asciidoctor.convert") version "1.5.9.2"
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.spring") version "1.5.10"
}

group = "com.flabedu"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

configurations {
    all {
        exclude("org.springframework.boot", "spring-boot-starter-logging")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.session:spring-session-data-redis")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.3")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.17")

    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

val snippetsDir by extra { file("build/generated-snippets") }

tasks {

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    withType<Test> {
        outputs.dir(snippetsDir)
        useJUnitPlatform()
    }

    asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn(test)
    }

    bootJar {
        dependsOn(asciidoctor)
        from("${asciidoctor.get().outputDir}/html5")
            .into("static/docs")
    }
}
