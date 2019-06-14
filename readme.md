## Dependency Injector
Dependency Injector is a simple and easy to use Java library that will allow you to inject dependencies without increasing final jar file size.

### Add to project
Gradle
```groovy
repositories {
	maven {
		url "https://repository.jaqobb.dev/maven-public/"
	}
}

dependencies {
	implementation "dev.jaqobb:dependencyinjector:{current version}"
}
```

Gradle Kotlin DSL
```kotlin
repository {
	maven("https://repository.jaqobb.dev/maven-public/")
}

dependencies {
	implementation("dev.jaqobb:dependencyinjector:{current version}")
}
```

Maven
```xml
<repositories>
	<repository>
		<url>https://repository.jaqobb.dev/maven-public/</url>
	</repository>
</repositories>

<dependencies>
	<dependency>
		<groupId>dev.jaqobb</groupId>
		<artifactId>dependencyinjector</artifactId>
		<version>{current version}</version>
		<scope>compile</scope>
	</dependency>
</dependencies>
```

### Usage example
Get the required info about the dependency you want to inject (group id, artifact id and version (and repository if the dependency is not located in the Maven Central Repository)).
Construct a new `Dependency` object using:
```java
Dependency dependency = new Dependency("groupId", "artifactId", "version");
```
or:
```java
Dependency dependency = new Dependency("groupId", "artifactId", "version", "repositoryUrl");
```
There are already a few repositories you can use without worrying about their url. You can get them from the `Repositories` class. If you want some repository to be added, please make either issue or pull request.

After you get your `Dependency` object you can inject it by using:
```java
DependencyInjector.injectDependency(dependency, classLoader);
```
`classLoader` is usually your main class' class loader (`this.getClass().getClassLoader()`). The provided class loader has to be an instance of `URLClassLoader`.

You can also inject dependencies without having to use `Dependency` object. In that case use the method with the same name but replace `dependency` parameter with the dependency's info (group id, artifact id and version (and repository if the dependency is not located in the Maven Central Repository))
