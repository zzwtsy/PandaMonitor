import com.google.protobuf.gradle.id
import tech.argonariod.gradle.jimmer.JimmerLanguage

// see https://danielliu1123.github.io/grpc-starter/docs/version
val grpcVersion = "1.65.0"
val protobufVersion = "3.25.1"

plugins {
    id("com.google.devtools.ksp") version "1.9.24+"
    id("tech.argonariod.gradle-plugin-jimmer") version "latest.release"
    id("com.google.protobuf") version "0.9.4"
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.spring") version "1.9.24"
}

group = "cn.zzwtsy"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

jimmer {
    version = "latest.release"
    language = JimmerLanguage.KOTLIN
    client {
        enableEmbeddedSwaggerUi = true
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/cn.dev33/sa-token-spring-boot-starter
//    implementation("cn.dev33:sa-token-spring-boot-starter:1.38.0")
    implementation(platform("io.github.danielliu1123:grpc-starter-dependencies:3.3.1"))
    implementation("io.github.danielliu1123:grpc-server-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.xerial:sqlite-jdbc:3.46.0.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${protobufVersion}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("grpc") {
                    option("@generated=omit")
                }
            }
        }
    }
}