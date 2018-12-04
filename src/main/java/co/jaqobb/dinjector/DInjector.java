/*
 * This file is a part of dinjector, licensed under the MIT License.
 *
 * Copyright (c) Jakub Zagórski (jaqobb)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.jaqobb.dinjector;

import co.jaqobb.dinjector.dependency.Dependency;
import co.jaqobb.dinjector.exception.DependencyDownloadException;
import co.jaqobb.dinjector.exception.DependencyInjectException;
import co.jaqobb.dinjector.java.JavaHelper;
import co.jaqobb.dinjector.repository.Repository;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

public final class DInjector {
  private static final Method GET_PLATFORM_CLASS_LOADER_METHOD;
  private static final Method ADD_URL_METHOD;

  static {
    try {
      GET_PLATFORM_CLASS_LOADER_METHOD = ClassLoader.class.getMethod("getPlatformClassLoader");
      GET_PLATFORM_CLASS_LOADER_METHOD.setAccessible(true);
      ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      ADD_URL_METHOD.setAccessible(true);
    } catch(NoSuchMethodException exception) {
      throw new InternalError("Could not cache addURL method", exception);
    }
  }

  private DInjector() {
  }

  public static void injectDependencies(Dependency[] dependencies) {
    if(dependencies == null) {
      throw new NullPointerException("dependencies cannot be null");
    }
    for(Dependency dependency : dependencies) {
      injectDependency(dependency);
    }
  }

  public static void injectDependency(String shorthandNotation) {
    injectDependency(Dependency.of(shorthandNotation));
  }

  public static void injectDependency(String groupId, String artifactId, String version) {
    injectDependency(Dependency.of(groupId, artifactId, version));
  }

  public static void injectDependency(String shorthandNotation, String repository) {
    injectDependency(Dependency.of(shorthandNotation, repository));
  }

  public static void injectDependency(String shorthandNotation, Repository repository) {
    injectDependency(Dependency.of(shorthandNotation, repository));
  }

  public static void injectDependency(String groupId, String artifactId, String version, String repository) {
    injectDependency(Dependency.of(groupId, artifactId, version, repository));
  }

  public static void injectDependency(String groupId, String artifactId, String version, Repository repository) {
    injectDependency(Dependency.of(groupId, artifactId, version, repository));
  }

  public static void injectDependency(Dependency dependency) {
    if(dependency == null) {
      throw new NullPointerException("dependency cannot be null");
    }
    ClassLoader classLoader;
    boolean isPlatform;
    try {
      if(JavaHelper.getJavaVersion() <= 52.0F) {
        classLoader = Thread.currentThread().getContextClassLoader();
        isPlatform = false;
      } else {
        classLoader = (ClassLoader) GET_PLATFORM_CLASS_LOADER_METHOD.invoke(null);
        isPlatform = true;
      }
    } catch(Exception exception) {
      throw new RuntimeException("Could not get ClassLoader");
    }
    String groupId = dependency.getGroupId();
    String artifactId = dependency.getArtifactId();
    String version = dependency.getVersion();
    String name = artifactId + "-" + version;
    String path = groupId.replace(".", File.separator) + File.separator + artifactId + File.separator + version;
    File dependenciesFolder = new File(".dependencies");
    File folder = new File(dependenciesFolder, path);
    File destination = new File(folder, name + ".jar");
    if(!destination.exists()) {
      try {
        URL url = dependency.getDownloadURL();
        try(InputStream inputStream = url.openStream()) {
          Files.copy(inputStream, destination.toPath());
        }
      } catch(Exception exception) {
        throw new DependencyDownloadException("Could not download dependency '" + name + "'", exception);
      }
    }
    if(!destination.exists()) {
      throw new DependencyDownloadException("Could not download dependency '" + name + "'");
    }
    try {
      if(isPlatform) {
        classLoader = new URLClassLoader(new URL[]{destination.toURI().toURL()}, classLoader);
      }
      ADD_URL_METHOD.invoke(classLoader, destination.toURI().toURL());
    } catch(Exception exception) {
      throw new DependencyInjectException("Could not inject dependency '" + name + "'", exception);
    }
  }
}