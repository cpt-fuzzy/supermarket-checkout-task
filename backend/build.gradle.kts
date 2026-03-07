plugins {
	java
	id("org.springframework.boot") version "4.0.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.diffplug.spotless") version "8.3.0"
	id("net.ltgt.errorprone") version "4.2.0"
}

group = "com.vangroenheesch"
version = "0.0.1-SNAPSHOT"
description = "Supermarket checkout proof-of-concept"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-webmvc")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
	testImplementation("com.tngtech.archunit:archunit-junit5:1.4.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	errorprone("com.google.errorprone:error_prone_core:2.43.0")
}

spotless {
	java {
		googleJavaFormat("1.35.0")
		formatAnnotations()
	}
}

tasks.named("check") {
	dependsOn("spotlessCheck")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
