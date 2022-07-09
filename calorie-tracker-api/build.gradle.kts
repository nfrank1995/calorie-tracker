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
	const val LOMBOK_VERSION = "1.18.24"
}

dependencies {
    compileOnly("org.projectlombok:lombok:${DependencyVersions.LOMBOK_VERSION}")
    testCompileOnly("org.projectlombok:lombok:${DependencyVersions.LOMBOK_VERSION}")
    annotationProcessor("org.projectlombok:lombok:${DependencyVersions.LOMBOK_VERSION}")
    testAnnotationProcessor("org.projectlombok:lombok:${DependencyVersions.LOMBOK_VERSION}")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
	implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.mockito:mockito-junit-jupiter:4.6.1")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:3.4.6")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0-M1")

}

tasks.test{
    useJUnitPlatform()
}
