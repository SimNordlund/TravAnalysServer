plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation ("org.hibernate.common:hibernate-commons-annotations:6.0.6.Final")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-autoconfigure")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("com.mysql:mysql-connector-j")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.0.5")
	implementation("com.fasterxml.jackson.core:jackson-core:2.17.1")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.1")
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.17.1")
	implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.1")


	implementation("org.yaml:snakeyaml")
	implementation("com.github.javafaker:javafaker:1.0.2") { exclude ("org.yaml") }
	implementation("org.modelmapper:modelmapper:3.2.0")

	implementation("org.springframework.boot:spring-boot-starter-mail")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.apache.commons:commons-text:1.12.0")

	implementation ("org.postgresql:postgresql:42.7.2")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
	testImplementation("org.springframework.security:spring-security-test:6.3.0")

	implementation("io.smallrye:jandex:3.3.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
