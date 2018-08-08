## DependencyInjector
Inject dependencies directly into your Java code without increasing jar file size.

### Purposes of this project
This project gives the possibility to use external dependencies in your applications without increasing the final jar size due to dependency shading.

### Requirements
All you need is Java 8 and optionally Maven or Gradle if you don't want to download sources on your own.

### How to use (Maven)
You need to add the dependency to your project pom.xml file:
```xml
<dependencies>
    <dependency>
        <groupId>co.jaqobb</groupId>
        <artifactId>dependencyinjector</artifactId>
        <version>2.0.0</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

### How to use (Gradle)
You need to add the dependency to your project build.gradle file:
```gradle
dependencies {
    compile "co.jaqobb:dependencyinjector:2.0.0"
}
```

### How to use (No Maven or Gradle)
You need to download the source code and add it to your current project.

### Example usage
```java
package co.jaqobb.dependencyinjector.test;

import co.jaqobb.dependencyinjector.DependencyInjector;

public final class DependencyInjectorTest {
    
    private DependencyInjectorTest() {
    }
    
    public static void main(String[] arguments) {
        // Inject Google Guice Library to the main class loader using shorthand notation.
        // No need to specify the repository because it's available in the Maven central one.
        DependencyInjector.injectDependency("com.google.inject:guice:4.0", this.getClass().getClassLoader());
        
        // Inject Google Guava Library to the main class loader using old notation.
        // No need to specify the repository because it's available in the Maven central one.
        DependencyInjector.injectDependency("com.google.guava", "guava", "19.0", this.getClass().getClassLoader());
        
        // Inject other library to the main class loader using shorthand notation.
        // In this case this dependency isn't available in the Maven central repository.
        // We need to use custom url instead.
        DependencyInjector.injectDependency("some.dependency:coolname:1.2.3", "https://link.to.this.dependency.repository");
        
        // Everything is done. You can use these libraries as before.
        // The only change is you don't have to shade them into your jar file.
    }
    
}
```

### Important
All downloaded dependencies are cached inside ".dependencies" folder in your user home director. This is used to prevent applications to download the same library several times and to allow other projects that use this project to use already downloaded dependencies.

### End
That's all! Thank you for using DependencyInjector!