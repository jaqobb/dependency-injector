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
import co.jaqobb.dinjector.repository.Repository;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

public final class DInjector {
  private static final Method ADD_URL_METHOD;

  static {
    try {
      ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
      ADD_URL_METHOD.setAccessible(true);
    } catch(final NoSuchMethodException exception) {
      throw new InternalError("Could not cache addURL method", exception);
    }
  }

  private DInjector() {
  }

  public static void injectDependencies(final Dependency[] dependencies, final ClassLoader classLoader) {
    if(dependencies == null) {
      throw new NullPointerException("Dependencies cannot be null");
    }
    for(final Dependency dependency : dependencies) {
      injectDependency(dependency, classLoader);
    }
  }

  public static void injectDependency(final String shorthandNotation, final ClassLoader classLoader) {
    injectDependency(Dependency.of(shorthandNotation), classLoader);
  }

  public static void injectDependency(final String groupId, final String artifactId, final String version, final ClassLoader classLoader) {
    injectDependency(Dependency.of(groupId, artifactId, version), classLoader);
  }

  public static void injectDependency(final String shorthandNotation, final String repository, final ClassLoader classLoader) {
    injectDependency(Dependency.of(shorthandNotation, repository), classLoader);
  }

  public static void injectDependency(final String shorthandNotation, final Repository repository, final ClassLoader classLoader) {
    injectDependency(Dependency.of(shorthandNotation, repository), classLoader);
  }

  public static void injectDependency(final String groupId, final String artifactId, final String version, final String repository, final ClassLoader classLoader) {
    injectDependency(Dependency.of(groupId, artifactId, version, repository), classLoader);
  }

  public static void injectDependency(final String groupId, final String artifactId, final String version, final Repository repository, final ClassLoader classLoader) {
    injectDependency(Dependency.of(groupId, artifactId, version, repository), classLoader);
  }

  public static void injectDependency(final Dependency dependency, final ClassLoader classLoader) {
    if(dependency == null) {
      throw new NullPointerException("Dependency cannot be null");
    }
    if(classLoader == null) {
      throw new NullPointerException("Class loader cannot be null");
    }
    final String groupId = dependency.getGroupId();
    final String artifactId = dependency.getArtifactId();
    final String version = dependency.getVersion();
    final String name = artifactId + "-" + version;
    final String path = groupId.replace(".", File.separator) + File.separator + artifactId + File.separator + version;
    final File dependenciesFolder = new File(".dependencies");
    final File folder = new File(dependenciesFolder, path);
    final File destination = new File(folder, name + ".jar");
    if(!destination.exists()) {
      try {
        final URL url = dependency.getDownloadUrl();
        try(final InputStream inputStream = url.openStream()) {
          Files.copy(inputStream, destination.toPath());
        }
      } catch(final Exception exception) {
        throw new DependencyDownloadException("Could not download dependency '" + name + "'", exception);
      }
    }
    if(!destination.exists()) {
      throw new DependencyDownloadException("Could not download dependency '" + name + "'");
    }
    try {
      ADD_URL_METHOD.invoke(classLoader, destination.toURI().toURL());
    } catch(final Exception exception) {
      throw new DependencyInjectException("Could not inject dependency '" + name + "'", exception);
    }
  }
}