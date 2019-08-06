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

package dev.jaqobb.dependencyinjector;

import dev.jaqobb.dependencyinjector.dependency.Dependency;
import dev.jaqobb.dependencyinjector.dependency.DependencyDownloadException;
import dev.jaqobb.dependencyinjector.dependency.DependencyInjectException;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
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
		} catch (Exception exception) {
			throw new InternalError("Could not cache addURL method", exception);
		}
	}

	private File dependenciesFolder;

	public DependencyInjector(File dependenciesFolder) {
		if (dependenciesFolder == null) {
			throw new IllegalArgumentException("dependenciesFolder cannot be null");
		}
		if (dependenciesFolder.isFile()) {
			throw new IllegalArgumentException("dependenciesFolder is not a folder");
		}
		this.dependenciesFolder = dependenciesFolder;
	}

	public File getDependenciesFolder() {
		return this.dependenciesFolder;
	}

	public void setDependenciesFolder(File folder) {
		if (folder == null) {
			throw new IllegalArgumentException("folder cannot be null");
		}
		if (folder.isFile()) {
			throw new IllegalArgumentException("folder is not a folder");
		}
		this.dependenciesFolder = folder;
	}

	public void injectDependencies(@NotNull Dependency[] dependencies, @NotNull ClassLoader classLoader) {
		for (Dependency dependency : dependencies) {
			this.injectDependency(dependency, classLoader);
		}
	}

	public void injectDependency(@NotNull String groupId, @NotNull String artifactId, @NotNull String version, @NotNull ClassLoader classLoader) {
		this.injectDependency(new Dependency(groupId, artifactId, version), classLoader);
	}

	public void injectDependency(@NotNull String groupId, @NotNull String artifactId, @NotNull String version, @NotNull String repository, @NotNull ClassLoader classLoader) {
		this.injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
	}

	public void injectDependency(@NotNull Dependency dependency, @NotNull ClassLoader classLoader) {
		if (!(classLoader instanceof URLClassLoader)) {
			throw new IllegalArgumentException("classLoader is not an instance of URLClassLoader");
		}
		String groupId = dependency.getGroupId();
		String artifactId = dependency.getArtifactId();
		String version = dependency.getVersion();
		String name = artifactId + "-" + version;
		String path = groupId.replace(".", File.separator) + File.separator + artifactId + File.separator + version;
		File destination = new File(new File(this.dependenciesFolder, path), name + ".jar");
		if (!destination.exists()) {
			try {
				URL url = dependency.getDownloadURL();
				try (InputStream inputStream = url.openStream()) {
					Files.copy(inputStream, destination.toPath());
				}
			} catch (Exception exception) {
				throw new DependencyDownloadException("Could not download dependency " + name, exception);
			}
		}
		if (!destination.exists()) {
			throw new DependencyDownloadException("Could not download dependency " + name);
		}
		try {
			ADD_URL_METHOD.invoke(classLoader, destination.toURI().toURL());
		} catch (Exception exception) {
			throw new DependencyInjectException("Could not inject dependency " + name, exception);
		}
	}
}
