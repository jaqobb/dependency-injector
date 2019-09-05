## DependencyInjector
A simple and easy to use Java library that allows you to inject dependencies without increasing final jar file size.

##### This project will no longer receive any updates as of 9/5/2019 due to the fact that everything I wanted to add is already here and this project does not really need anything more.

### Add to project
Gradle
```groovy
repositories {
	jcenter()
}

dependencies {
	implementation "dev.jaqobb:dependencyinjector:2.2.7"
}
```

Gradle Kotlin DSL
```kotlin
repository {
	jcenter()
}

dependencies {
	implementation("dev.jaqobb:dependencyinjector:2.2.7")
}
```

### Usage example
Get the required information about the dependency you want to inject (group id, artifact id and version (and repository if the dependency isn't located in the Maven Central Repository)).
Construct a new `Dependency` object using:
```java
Dependency dependency = new Dependency("groupId", "artifactId", "version");
```
or:
```java
Dependency dependency = new Dependency("groupId", "artifactId", "version", "repositoryUrl");
```
There are already a few repositories you can use without worrying about their url. You can get them from the `Repositories` class. If you want some repository to be added, please make either issue or pull request.

After you get your `Dependency` object you can now create a `DependencyInjector` object using:
```java
DependencyInjector dependencyInjector = new DependencyInjector(dependenciesFolder);
```
`dependenciesFolder` is a folder where all dependencies will be cached.

After this is done you can inject your dependency using:
```java
dependencyInjector.injectDependency(dependency, classLoader);
```
`classLoader` is usually your main class' class loader (`this.getClass().getClassLoader()`). However, the provided class loader must be an instance of `URLClassLoader`.

You can also inject dependencies without having to use `Dependency` object. In that case use the method with the same name but replace `dependency` parameter with the dependency's information (group id, artifact id and version (and repository if the dependency isn't in the Maven Central Repository))
