## Dependency-Injector
Simple library that injects maven dependencies directly into your Java code without increasing jar file size.

### Purposes of this project
Purpose of this projects is actually really simple: to use maven dependencies in your program without increasing final jar size due to dependency shading.

### Requirements
All you need is Java 8 and optionally Maven if you don't want to download sources on your own.

### How to use (Maven)
You need to add repository to your project's pom.xml:
```xml
<repositories>
	<repository>
		<id>jaqobb-repo</id>
		<url>https://repo.jaqobb.co/repository/maven-snapshots/</url>
	</repository>
</repositories>
```
and add dependency:
```xml
<dependencies>
	<dependency>
		<groupId>co.jaqobb</groupId>
		<artifactId>dependency-injector</artifactId>
		<version>1.0-SNAPSHOT</version>
		<scope>compile</scope>
	</dependency>
</dependencies>
```
Yeah, you actually need to compile this library if you are using Maven due to it's not shaded anywhere by default.

### How to use (No Maven)
Simply download source and add it your project.

### API
Everything you should care about is the DependencyInjector class, everything starts in this class.

Before your whole code starts, you need to use one the accessible inject methods:
```java
// Injects provided dependencies into the provided class loader.
injectDependencies(Dependency[] dependencies, ClassLoader classLoader);

// Injects dependency with the provided group id, artifact id and version from maven central repository into the provided class loader.
injectDependency(String groupId, String artifactId, String version, ClassLoader classLoader);

// Injects dependency with the provided group id, artifact id and version from the provided repository into the provided class loader.
injectDependency(String groupId, String artifactId, String version, Repository repository, ClassLoader classLoader);

// Injects provided dependency into the provided class loader.
injectDependency(Dependency dependency, ClassLoader classLoader);
```

You can create a repository class by using:
```java
new Repository(String url);
```

You can also create a dependency class by using:
```java
new Dependency(String groupId, String artifactId, String version, Repository repository)
```
or:
```java
new Dependency(String groupId, String artifactId, String version)
```
If the repository is not provided, maven central repository will be used.

### Important

All downloaded dependencies are cached inside ".dependencies" folder in your user home folder. The reason they are saved like that is to prevent program to download the same library several times and to allow other projects that use this project to use already downloaded dependencies.

### End
That's all! Thank you for using Dependency-Injector!