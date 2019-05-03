plugins {
	`java-library`
}

group = "dev.jaqobb"
version = "2.2.5"

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

defaultTasks("clean", "build", "sourcesJar")

tasks {
	test {
		useJUnitPlatform {
			includeEngines("junit-jupiter")
		}
	}
}

task<Jar>("sourcesJar") {
	from(sourceSets["main"].allSource)
	archiveClassifier.set("sources")
}

repositories {
	mavenCentral()
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
}
