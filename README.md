## Dependency-Injector
Inject dependencies directly into your Java code without increasing jar file size.

### Purposes of this project
Purpose of this projects is actually really simple: to use dependencies in your program without increasing final jar size due to dependency shading.

### Requirements
All you need is Java 8 and optionally Maven/Gradle if you don't want to download sources on your own.

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
    <version>1.1.4-SNAPSHOT</version>
    <scope>compile</scope>
  </dependency>
</dependencies>
```
You need to compile this library due to it's not shaded anywhere by default.

### How to use (Gradle)
You need to add repository to your project's build.gradle:
```
repositories {
  maven {
    url "https://repo.jaqobb.co/repository/maven-snapshots/"
  }
}
```
and add dependency:
```
dependencies {
  compile "co.jaqobb:dependency-injector:1.1.4-SNAPSHOT"
}
```
You need to compile this library due to it's not shaded anywhere by default.

### How to use (No Maven/Gradle)
Simply download source and add it to your current project.

### API
Everything you should care about is the DependencyInjector class, everything starts in this class.

Before your whole code starts, you need to use one the accessible inject methods:
```java
// Injects provided dependencies into the provided class loader.
injectDependencies(Dependency[] dependencies, ClassLoader classLoader);
e.g.: injectDependencies(new Dependency[] {new Dependency("com.google.code.gson", "gson", "2.8.5"), new Dependency("co.jaqobb:namemc-api:1.2.3-SNAPSHOT", Repositories.JAQOBB_SNAPSHOTS)}, this.getClass().getClassLoader());

// Injects dependency with the provided group id, artifact id and version from maven central repository into the provided class loader.
injectDependency(String groupId, String artifactId, String version, ClassLoader classLoader);
e.g.: injectDependency("com.google.code.gson", "gson", "2.8.5", this.getClass().getClassLoader());

// Injects dependency with the provided group id, artifact id and version from the provided repository into the provided class loader.
injectDependency(String groupId, String artifactId, String version, Repository repository, ClassLoader classLoader);
e.g.: injectDependency("co.jaqobb", "namemc-api", "1.2.3-SNAPSHOT", Repositories.JAQOBB_SNAPSHOTS, this.getClass().getClassLoader());

// Injects provided dependency into the provided class loader.
injectDependency(Dependency dependency, ClassLoader classLoader);
e.g.: injectDependency(new Dependency("com.google.code.gson", "gson", "2.8.5"), this.getClass().getClassLoader());
e.g.: injectDependency(new Dependency("co.jaqobb:namemc-api:1.2.3-SNAPSHOT", Repositories.JAQOBB_SNAPSHOTS), this.getClass().getClassLoader());
```

You can create a repository class by using:
```java
// Creates new repository with the provided url.
new Repository(String url);
e.g.: new Repository("https://repo.your-name.com/repository/maven-snapshots/");
```

You can also create a dependency class by using:
```java
// Creates new dependency with the provided group id, artifact id, version and repository.
new Dependency(String groupId, String artifactId, String version, Repository repository);
e.g.: new Dependency("co.jaqobb", "namemc-api", "1.2.3-SNAPSHOT", Repositories.JAQOBB_SNAPSHOTS);
```
or:
```java
// Creates new dependency with the provided group id, artifact id, version.
new Dependency(String groupId, String artifactId, String version);
e.g.: new Dependency("com.google.code.gson", "gson", "2.8.5");
```

Since version 1.1-SNAPSHOT, you can use shorthand notation to create/inject dependencies. Shorthand notation is just a shortened form of writing group id, artifact id and version (\<group id\>:\<artifact id\>:\<version\>).

You can create dependency class with shorthand notation using:
```java
// Creates new dependency with the provided shorthand notation and repository.
new Dependency(String shorthandNotation, Repository repository);
e.g.: new Dependency("co.jaqobb:namemc-api:1.2.3-SNAPSHOT", Repositories.JAQOBB_SNAPSHOTS);
```
or:
```java
// Creates new dependency with the provided shorthand notation.
new Dependency(String shorthandNotation);
e.g.: new Dependency("com.google.code.gson:gson:2.8.5");
```

And in the DependencyInjector class you can use shorthand notation as well:
```java
// Injects dependency with the provided shorthand notation from maven central repository into the provided class loader.
injectDependency(String shorthandNotation, ClassLoader classLoader);
e.g.: injectDependency("co.jaqobb:namemc-api:1.2.3-SNAPSHOT", this.getClass().getClassLoader());

// Injects dependency with the provided shorthand notation from the provided repository into the provided class loader.
injectDependency(String shorthandNotation, Repository repository, ClassLoader classLoader);
e.g.: injectDependency("co.jaqobb:namemc-api:1.2.3-SNAPSHOT", Repositories.JAQOBB_SNAPSHOTS, this.getClass().getClassLoader());
```

If the repository is not provided, maven central repository will be used instead.

Dependency Injector also provides preset dependencies and repositories available in the Dependencies and Repositories class.

If you want me to add either a dependency or a repository, just contact me through my discord: jaqobb#6998, or through this project's issues section.

### Important

All downloaded dependencies are cached inside ".dependencies" folder in your user home folder. The reason they are saved like that is to prevent program to download the same library several times and to allow other projects that use this project to use already downloaded dependencies.

### End
That's all! Thank you for using Dependency Injector!