/*
 * MIT License
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

package dev.jaqobb.dependency_injector;

import dev.jaqobb.dependency_injector.dependency.Dependency;
import dev.jaqobb.dependency_injector.dependency.DependencyDownloadException;
import dev.jaqobb.dependency_injector.dependency.DependencyInjectException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import org.jetbrains.annotations.NotNull;

public final class DependencyInjector {

	private static final Method ADD_URL_METHOD;

	static {
		try {
			ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			ADD_URL_METHOD.setAccessible(true);
		} catch(NoSuchMethodException exception) {
			throw new InternalError("Could not cache addURL method", exception);
		}
	}

	private DependencyInjector() {
		throw new UnsupportedOperationException("Cannot create instance of utility class");
	}

	public static void injectDependencies(@NotNull Dependency[] dependencies, @NotNull URLClassLoader classLoader) {
		for(Dependency dependency : dependencies) {
			injectDependency(dependency, classLoader);
		}
	}

	public static void injectDependency(@NotNull String groupId, @NotNull String artifactId, @NotNull String version, @NotNull URLClassLoader classLoader) {
		injectDependency(new Dependency(groupId, artifactId, version), classLoader);
	}

	public static void injectDependency(@NotNull String groupId, @NotNull String artifactId, @NotNull String version, @NotNull String repository, @NotNull URLClassLoader classLoader) {
		injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
	}

	public static void injectDependency(@NotNull Dependency dependency, @NotNull URLClassLoader classLoader) {
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
				URL url = dependency.getDownloadUrl();
				try(InputStream inputStream = url.openStream()) {
					Files.copy(inputStream, destination.toPath());
				}
			} catch(IOException exception) {
				throw new DependencyDownloadException("Could not download dependency " + name, exception);
			}
		}
		if(!destination.exists()) {
			throw new DependencyDownloadException("Could not download dependency " + name);
		}
		try {
			ADD_URL_METHOD.invoke(classLoader, destination.toURI().toURL());
		} catch(IllegalAccessException | InvocationTargetException | MalformedURLException exception) {
			throw new DependencyInjectException("Could not inject dependency " + name, exception);
		}
	}
}
