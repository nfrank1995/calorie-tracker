plugins {
	id("org.springframework.boot") version "2.6.7"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	`java-library`
}

group = "nfrank1995.de"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

object DependencyVersions {
	const val TESTCONTAINERS_VERSION = "1.16.2"
	const val LOMBOK_VERSION = "1.18.4"
}

dependencies {

    compileOnly("org.projectlombok:lombok:${DependencyVersions.LOMBOK_VERSION}")
    testCompileOnly("org.projectlombok:lombok:${DependencyVersions.LOMBOK_VERSION}")
    annotationProcessor("org.projectlombok:lombok:${DependencyVersions.LOMBOK_VERSION}")
    testAnnotationProcessor("org.projectlombok:lombok:${DependencyVersions.LOMBOK_VERSION}")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.7.0")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.8")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:mongodb")
	testImplementation("org.testcontainers:postgresql")


}

dependencyManagement {
	imports {
		mavenBom("org.testcontainers:testcontainers-bom:${DependencyVersions.TESTCONTAINERS_VERSION}")
	}
}

tasks.test {
	useJUnitPlatform()
}
