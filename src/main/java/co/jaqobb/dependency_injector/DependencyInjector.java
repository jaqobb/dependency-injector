/*
 * This file is a part of dependency-injector, licensed under the MIT License.
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
package co.jaqobb.dependency_injector;

import co.jaqobb.dependency_injector.dependency.Dependency;
import co.jaqobb.dependency_injector.exception.dependency.DependencyDownloadException;
import co.jaqobb.dependency_injector.exception.dependency.DependencyInjectException;
import co.jaqobb.dependency_injector.repository.Repository;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

public final class DependencyInjector {
	private static Method addURLMethod;

	static {
		try {
			DependencyInjector.addURLMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			DependencyInjector.addURLMethod.setAccessible(true);
		} catch (NoSuchMethodException exception) {
			throw new InternalError("Could not cache addURL method", exception);
		}
	}

	private DependencyInjector() {
	}

	public static void injectDependencies(Dependency[] dependencies, URLClassLoader classLoader) {
		if (dependencies == null) {
			throw new NullPointerException("dependencies cannot be null");
		}
		for (Dependency dependency : dependencies) {
			DependencyInjector.injectDependency(dependency, classLoader);
		}
	}

	public static void injectDependency(String shorthandNotation, URLClassLoader classLoader) {
		DependencyInjector.injectDependency(new Dependency(shorthandNotation), classLoader);
	}

	public static void injectDependency(String groupId, String artifactId, String version, URLClassLoader classLoader) {
		DependencyInjector.injectDependency(new Dependency(groupId, artifactId, version), classLoader);
	}

	public static void injectDependency(String shorthandNotation, String repository, URLClassLoader classLoader) {
		DependencyInjector.injectDependency(new Dependency(shorthandNotation, repository), classLoader);
	}

	public static void injectDependency(String shorthandNotation, Repository repository, URLClassLoader classLoader) {
		DependencyInjector.injectDependency(new Dependency(shorthandNotation, repository), classLoader);
	}

	public static void injectDependency(String groupId, String artifactId, String version, String repository, URLClassLoader classLoader) {
		DependencyInjector.injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
	}

	public static void injectDependency(String groupId, String artifactId, String version, Repository repository, URLClassLoader classLoader) {
		DependencyInjector.injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
	}

	public static void injectDependency(Dependency dependency, URLClassLoader classLoader) {
		if (dependency == null) {
			throw new NullPointerException("dependency cannot be null");
		}
		if(classLoader == null){
			throw new NullPointerException("classLoader cannot be null");
		}
		String groupId = dependency.getGroupId();
		String artifactId = dependency.getArtifactId();
		String version = dependency.getVersion();
		String name = artifactId + "-" + version;
		String path = groupId.replace(".", File.separator) + File.separator + artifactId + File.separator + version;
		File dependenciesFolder = new File(".dependencies");
		File folder = new File(dependenciesFolder, path);
		File destination = new File(folder, name + ".jar");
		if (!destination.exists()) {
			try {
				URL url = dependency.getDownloadURL();
				try (InputStream inputStream = url.openStream()) {
					Files.copy(inputStream, destination.toPath());
				}
			} catch (Exception exception) {
				throw new DependencyDownloadException("Could not download dependency '" + name + "'", exception);
			}
		}
		if (!destination.exists()) {
			throw new DependencyDownloadException("Could not download dependency '" + name + "'");
		}
		try {
			DependencyInjector.addURLMethod.invoke(classLoader, destination.toURI().toURL());
		} catch (Exception exception) {
			throw new DependencyInjectException("Could not inject dependency '" + name + "'", exception);
		}
	}
}