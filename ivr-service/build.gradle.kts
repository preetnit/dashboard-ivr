import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.12.RELEASE"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("plugin.spring") version "1.5.31"
}

group = "com.design"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
val SBReleaseVersion = "2.3.12.RELEASE"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter:${SBReleaseVersion}")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation("org.springframework.boot:spring-boot-starter-webflux:${SBReleaseVersion}")

	implementation("org.springframework.boot:spring-boot-starter-actuator:${SBReleaseVersion}")
	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.5.12")
	implementation("io.github.microutils:kotlin-logging:1.7.7")

	//implementation("org.springframework.boot:spring-boot-starter-security:${SBReleaseVersion}")
	//implementation("org.springframework.security:spring-security-oauth2-client")
	//implementation("org.springframework.security:spring-security-oauth2-resource-server")
	//implementation("org.springframework.security:spring-security-oauth2-jose")

	implementation("org.springframework.boot:spring-boot-starter-data-mongodb:${SBReleaseVersion}")

	implementation("org.apache.commons:commons-csv:1.9.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
