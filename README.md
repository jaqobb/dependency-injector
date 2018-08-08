## DependencyInjector
Inject dependencies directly into your Java code without increasing jar file size.

### Purposes of this project
This project gives the possibility to use external dependencies in your applications without increasing the final jar size due to dependency shading.

### Requirements
All you need is Java 8.

### How to use
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
        // No need to specify the repository because this dependency is available in the Maven central repository.
        DependencyInjector.injectDependency("com.google.inject:guice:4.0", this.getClass().getClassLoader());
        
        // Inject Google Guava Library to the main class loader using old notation.
        // No need to specify the repository because this dependency is available in the Maven central repository.
        DependencyInjector.injectDependency("com.google.guava", "guava", "19.0", this.getClass().getClassLoader());
        
        // Inject other library to the main class loader using shorthand notation.
        // In this case this dependency isn't available in the Maven central repository.
        // We need to use the custom url instead.
        DependencyInjector.injectDependency("some.dependency:coolname:1.2.3", "https://link.to.this.dependency.repository", this.getClass().getClassLoader());
        
        // Everything is done. You can use libraries above as before.
        // The only change is you don't have to shade them into your jar file.
    }
    
}
```

### Important
All downloaded dependencies are cached inside ".dependencies" folder in your user home director. This is used to prevent applications to download the same library several times and to allow other projects that use this project to use already downloaded dependencies.

### End
That's all! Thank you for using DependencyInjector!