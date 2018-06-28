/*
 * This file is a part of dependency-injector, licensed under the MIT License.
 *
 * Copyright (c) jaqobb (Jakub Zagórski) <jaqobb@jaqobb.co>
 * Copyright (c) contributors
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

package co.jaqobb.dependency.injector;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Objects;

import co.jaqobb.dependency.injector.dependency.Dependency;
import co.jaqobb.dependency.injector.exception.DependencyDownloadException;
import co.jaqobb.dependency.injector.exception.DependencyInjectException;
import co.jaqobb.dependency.injector.exception.MissingShorthandNotationInfoException;
import co.jaqobb.dependency.injector.repository.Repository;

/**
 * Main class used to inject dependencies.
 */
public final class DependencyInjector
{
	/**
	 * Cached add url method.
	 */
	private static final Method ADD_URL_METHOD;

	/**
	 * Caches add url method.
	 *
	 * @throws InternalError If the error occured while trying to cache add url method.
	 */
	static
	{
		try
		{
			ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			ADD_URL_METHOD.setAccessible(true);
		}
		catch (NoSuchMethodException exception)
		{
			throw new InternalError("Could not cache add url method", exception);
		}
	}

	/**
	 * Private constructor to make sure no one will initialize this class.
	 */
	private DependencyInjector()
	{
	}

	/**
	 * Injects the given dependencies into the given class loader.
	 *
	 * @param dependencies An array of dependencies to inject.
	 * @param classLoader A class loader which all dependencies will be injected into.
	 *
	 * @throws NullPointerException If the given dependencies or any depenendency or class loader is null.
	 * @throws DependencyDownloadException If the error occurred while trying to download any dependency.
	 * @throws DependencyInjectException If the error occurred while trying inject any dependency.
	 * @throws MissingShorthandNotationInfoException If shorthand notation was used to create any dependency and the shorthand notation is missing group id, artifact id or version.
	 */
	public static void injectDependencies(Dependency[] dependencies, ClassLoader classLoader)
	{
		Objects.requireNonNull(dependencies, "dependencies");
		for (Dependency dependency : dependencies)
		{
			injectDependency(dependency, classLoader);
		}
	}

	/**
	 * Injects a dependency from the Maven central repository using the given shorthand notation into the given class loader.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 * @param classLoader A class loader which dependency will be injected into.
	 *
	 * @throws NullPointerException If the given shorthand notation or class loader is null.
	 * @throws DependencyDownloadException If the error occurred while trying to download dependency.
	 * @throws DependencyInjectException If the error occurred while trying inject dependency.
	 * @throws MissingShorthandNotationInfoException If the shorthand notation is missing group id, artifact id or version.
	 */
	public static void injectDependency(String shorthandNotation, ClassLoader classLoader)
	{
		injectDependency(new Dependency(shorthandNotation), classLoader);
	}

	/**
	 * Injects a dependency with the given group id, artifact id, and version from the Maven central repository into the given class loader.
	 *
	 * @param groupId An group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the version.
	 * @param classLoader A class loader which dependency will be injected into.
	 *
	 * @throws NullPointerException If the given group id, artifact id, version or class loader is null.
	 * @throws DependencyDownloadException If the error occurred while trying to download dependency.
	 * @throws DependencyInjectException If the error occurred while trying inject dependency.
	 */
	public static void injectDependency(String groupId, String artifactId, String version, ClassLoader classLoader)
	{
		injectDependency(new Dependency(groupId, artifactId, version), classLoader);
	}

	/**
	 * Injects dependency from the given repository using the given shorthand notation into the given class loader.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 * @param classLoader A class loader which dependency will be injected into.
	 *
	 * @throws NullPointerException If the given shorthand notation or class loader is null.
	 * @throws DependencyDownloadException If the error occurred while trying to download dependency.
	 * @throws DependencyInjectException If the error occurred while trying inject dependency.
	 * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version.
	 */
	public static void injectDependency(String shorthandNotation, String repository, ClassLoader classLoader)
	{
		injectDependency(new Dependency(shorthandNotation, repository), classLoader);
	}

	/**
	 * Injects a dependency from the given repository using the given shorthand notation into the given class loader.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 * @param classLoader A class loader which dependency will be injected into.
	 *
	 * @throws NullPointerException If the given shorthand notation or class loader is null.
	 * @throws DependencyDownloadException If the error occurred while trying to download dependency.
	 * @throws DependencyInjectException If the error occurred while trying inject dependency.
	 * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version.
	 */
	public static void injectDependency(String shorthandNotation, Repository repository, ClassLoader classLoader)
	{
		injectDependency(new Dependency(shorthandNotation, repository), classLoader);
	}

	/**
	 * Injects a dependency with the given group id, artifact id, and version from the given repository into the given class loader.
	 *
	 * @param groupId A group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the dependency.
	 * @param repository A repository which holds dependency with the given group id, artifact id and version.
	 * @param classLoader A class loader which dependency will be injected into.
	 *
	 * @throws NullPointerException If the given group id, artifact id, version, repository or class loader is null.
	 * @throws DependencyDownloadException If the error occurred while trying to download dependency.
	 * @throws DependencyInjectException If the error occurred while trying inject dependency.
	 */
	public static void injectDependency(String groupId, String artifactId, String version, String repository, ClassLoader classLoader)
	{
		injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
	}

	/**
	 * Injects a dependency with the given group id, artifact id, and version from the given repository into the given class loader.
	 *
	 * @param groupId A group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the dependency.
	 * @param repository A repository which holds dependency with the given group id, artifact id and version.
	 * @param classLoader A class loader which dependency will be injected into.
	 *
	 * @throws NullPointerException If the given group id, artifact id, version, repository or class loader is null.
	 * @throws DependencyDownloadException If the error occurred while trying to download dependency.
	 * @throws DependencyInjectException If the error occurred while trying inject dependency.
	 */
	public static void injectDependency(String groupId, String artifactId, String version, Repository repository, ClassLoader classLoader)
	{
		injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
	}

	/**
	 * Injects the given dependency into the given class loader.
	 *
	 * @param dependency A dependency to inject.
	 * @param classLoader A class loader which the given dependency will be injected into.
	 *
	 * @throws NullPointerException If the given dependency or class loader is null.
	 * @throws DependencyDownloadException If the error occurred while trying to download the given dependency.
	 * @throws DependencyInjectException If the error occurred while trying inject the given dependency.
	 * @throws MissingShorthandNotationInfoException If a shorthand notation was used to create the given dependency and the shorthand notation is missing group id, artifact id or version.
	 */
	public static void injectDependency(Dependency dependency, ClassLoader classLoader)
	{
		Objects.requireNonNull(dependency, "dependency");
		Objects.requireNonNull(classLoader, "classLoader");
		String dependencyGroupId = dependency.getGroupId();
		String dependencyArtifactId = dependency.getArtifactId();
		String dependencyVersion = dependency.getVersion();
		String dependencyName = dependencyArtifactId + "-" + dependencyVersion;
		String dependencyPath = dependencyGroupId.replace(".", File.separator) + File.separator + dependencyArtifactId + File.separator + dependencyVersion;
		File dependenciesFolder = new File(".dependencies");
		File dependencyFolder = new File(dependenciesFolder, dependencyPath);
		File dependencyDestination = new File(dependencyFolder, dependencyName + ".jar");
		if (!dependencyDestination.exists())
		{
			try
			{
				URL url = dependency.getDownloadUrl();
				try (InputStream inputStream = url.openStream())
				{
					Files.copy(inputStream, dependencyDestination.toPath());
				}
			}
			catch (Exception exception)
			{
				throw new DependencyDownloadException("Could not download dependency '" + dependencyName + "'", exception);
			}
		}
		if (!dependencyDestination.exists())
		{
			throw new DependencyDownloadException("Could not download dependency '" + dependencyName + "'");
		}
		try
		{
			ADD_URL_METHOD.invoke(classLoader, dependencyDestination.toURI().toURL());
		}
		catch (Exception exception)
		{
			throw new DependencyInjectException("Could not inject dependency '" + dependencyName + "'", exception);
		}
	}
}