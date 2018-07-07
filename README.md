# Project end
This project is no longer maintained by me. If someone want to take care of the project, please contact me via Discord: jaqobb#6998.

## Dependency-Injector
Inject dependencies directly into your Java code without increasing jar file size.

### Purposes of this project
This project is supposed to give a possibility to use external dependencies in your programs without increasing final jar size due to dependency shading.

### Requirements
All you need is Java 8 and optionally Maven if you don't want to download sources on your own.

### How to use (Maven)
You need to add repository to your project pom.xml file:
```xml
<repositories>
	<repository>
		<id>jaqobb-repo</id>
		<url>https://repo.jaqobb.co/repository/maven-snapshots/</url>
	</repository>
</repositories>
```
and then add dependency:
```xml
<dependencies>
	<dependency>
		<groupId>co.jaqobb</groupId>
		<artifactId>dependency-injector</artifactId>
		<version>1.1.7-SNAPSHOT</version>
		<scope>compile</scope>
	</dependency>
</dependencies>
```
You need to compile this library due to it's not shaded anywhere by default.

### How to use (No Maven)
Simply download source and add it to your current project.

### API
Everything starts in the DependencyInjector class.

Before your whole code starts, you need to use one of the some accessible inject methods:
```java
// Injects provided dependencies into the provided class loader.
injectDependencies(Dependency[] dependencies, ClassLoader classLoader);

// Injects dependency with the provided group id, artifact id and version from Maven central repository into the provided class loader.
injectDependency(String groupId, String artifactId, String version, ClassLoader classLoader);

// Injects dependency with the provided group id, artifact id and version from the provided repository into the provided class loader.
injectDependency(String groupId, String artifactId, String version, Repository repository, ClassLoader classLoader);

// Injects provided dependency into the provided class loader.
injectDependency(Dependency dependency, ClassLoader classLoader);
```

You can create a repository class by using:
```java
// Creates new repository with the provided url.
new Repository(String url);
```

You can also create a dependency class by using:
```java
// Creates new dependency with the provided group id, artifact id, version and repository.
new Dependency(String groupId, String artifactId, String version, Repository repository);
```
or:
```java
// Creates new dependency with the provided group id, artifact id, version.
new Dependency(String groupId, String artifactId, String version);
```

Since the 1.1-SNAPSHOT version you can use shorthand notation to create/inject dependencies. Shorthand notation is just a shortened form of writing group id, artifact id and version (\<group id\>:\<artifact id\>:\<version\>).

You can create a dependency class with shorthand notation using:
```java
// Creates new dependency with the provided shorthand notation and repository.
new Dependency(String shorthandNotation, Repository repository);
```
or:
```java
// Creates new dependency with the provided shorthand notation.
new Dependency(String shorthandNotation);
```

And in the DependencyInjector class you can use shorthand notation as well:
```java
// Injects dependency with the provided shorthand notation from Maven central repository into the provided class loader.
injectDependency(String shorthandNotation, ClassLoader classLoader);

// Injects dependency with the provided shorthand notation from the provided repository into the provided class loader.
injectDependency(String shorthandNotation, Repository repository, ClassLoader classLoader);
```

If the repository is not provided, Maven central repository will be used instead.

Dependency-Injector also provides repositories available in the Repositories class.

If you want me to add a repository, just contact me through my Discord: jaqobb#6998 or through this project issues section.

### Important

All downloaded dependencies are cached inside ".dependencies" folder in your user home folder. The reason they are saved like that is to prevent program to download the same library several times and to allow other projects that use this project to use already downloaded dependencies.

### End
That's all! Thank you for using Dependency-Injector!
