import com.jfrog.bintray.gradle.BintrayExtension

plugins {
	`java-library`
	`maven-publish`
	id("com.jfrog.bintray") version "1.8.4"
}

group = "dev.jaqobb"
version = "2.2.6"
description = "Java library that allows to inject dependencies without increasing final jar file size"

java {
	sourceCompatibility = JavaVersion.VERSION_11
	targetCompatibility = JavaVersion.VERSION_11
}

defaultTasks("clean", "build", "sourcesJar", "bintrayUpload")

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
	jcenter()
}

dependencies {
	compileOnly("org.jetbrains:annotations:17.0.0")
	testRuntime("org.junit.jupiter:junit-jupiter-engine:5.4.2")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = project.group as String
			artifactId = project.name.toLowerCase()
			version = project.version as String
			from(components["java"])
			artifact(tasks["sourcesJar"])
		}
	}
}

configure<BintrayExtension> {
	user = properties["bintray-user"] as String?
	key = properties["bintray-api-key"] as String?
	publish = true
	setPublications("maven")
	pkg(closureOf<BintrayExtension.PackageConfig> {
		repo = properties["bintray-repository"] as String?
		name = project.name
		desc = project.description
		websiteUrl = "https://github.com/jaqobb/DependencyInjector"
		issueTrackerUrl = "$websiteUrl/issues"
		vcsUrl = "$websiteUrl.git"
		setLicenses("MIT")
		setLabels("java", "library", "dependency", "injector")
	})
}
