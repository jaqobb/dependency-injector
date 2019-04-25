plugins {
  this.`java-library`
}

group = "dev.jaqobb"
version = "2.2.5"

java {
  this.sourceCompatibility = JavaVersion.VERSION_11
  this.targetCompatibility = JavaVersion.VERSION_11
}

defaultTasks("clean", "build", "sourcesJar")

tasks {
  test {
    this.useJUnitPlatform {
      this.includeEngines("junit-jupiter")
    }
  }
}

task<Jar>("sourcesJar") {
  this.from(sourceSets["main"].allSource)
  this.archiveClassifier.set("sources")
}

repositories {
  this.mavenCentral()
}

dependencies {
  this.testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
}