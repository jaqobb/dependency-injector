/*
 * MIT License
 *
 * Copyright (c) 2018 Jakub Zagórski
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
import co.jaqobb.dependency.injector.repository.Repository;

/**
 * Main class used to inject dependencies.
 */
public final class DependencyInjector
{
	/**
	 * Cached addURL method.
	 */
	private static final Method ADD_URL_METHOD;

	/**
	 * Caches addURL method.
	 *
	 * @throws InternalError if the error occured while trying to cache addURL method.
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
			throw new InternalError("Could not cache addURL method", exception);
		}
	}

	/**
	 * Useless constructor, just to make sure
	 * no one will initialize this class.
	 */
	private DependencyInjector()
	{
	}

	/**
	 * Injects the given {@code dependencies}
	 * into the given {@code classLoader}.
	 *
	 * @param dependencies an array of {@code Dependency} to inject.
	 * @param classLoader  a class loader which {@code dependencies}
	 *                     will be injected into.
	 *
	 * @throws NullPointerException     if the given {@code dependencies} or any {@code Dependency} in the given
	 *                                  {@code dependencies} or the given {@code classLoader} is {@code null}.
	 * @throws RuntimeException         if the error occurred while trying to download or inject {@code Dependency}.
	 * @throws IllegalArgumentException if shorthand notation was used to create any instance of {@class Dependency}
	 *                                  and the shorthand notation does not have group id, artifact id or version.
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
	 * Injects {@code Dependency} from the maven central repository
	 * using the given {@code shorthandNotation} into the given {@code classLoader}.
	 *
	 * @param shorthandNotation a shorthand notation (<group id>:<artifact id>:<version>).
	 * @param classLoader       a class loader which {@code Dependency} will be injected into.
	 *
	 * @throws NullPointerException     if the given {@code shorthandNotation} or the given {@code classLoader} is {@code null}.
	 * @throws RuntimeException         if the error occurred while trying to download or inject {@code Dependency}.
	 * @throws IllegalArgumentException if the {@code shorthandNotation} does not have group id, artifact id or version.
	 */
	public static void injectDependency(String shorthandNotation, ClassLoader classLoader)
	{
		injectDependency(new Dependency(shorthandNotation), classLoader);
	}

	/**
	 * Injects {@code Dependency} with the given {@code groupId}
	 * and the given {@code artifactId} and the given {@code version}
	 * from the maven central repository into the given {@code classLoader}.
	 *
	 * @param groupId     an group id of the {@code Dependency}
	 * @param artifactId  an artifact id of the {@code Dependency}
	 * @param version     a version of the {@code Dependency}
	 * @param classLoader a class loader which {@code Dependency}
	 *                    will be injected into.
	 *
	 * @throws NullPointerException if the given {@code groupId} or the given {@code artifactId} or the given
	 *                              {@code version} or the given {@code classLoader} is {@code null}.
	 * @throws RuntimeException     if the error occurred while trying to download or inject {@code Dependency}.
	 */
	public static void injectDependency(String groupId, String artifactId, String version, ClassLoader classLoader)
	{
		injectDependency(new Dependency(groupId, artifactId, version), classLoader);
	}

	/**
	 * Injects {@code Dependency} from the given {@code repository}
	 * using the given {@code shorthandNotation} into the given {@code classLoader}.
	 *
	 * @param shorthandNotation a shorthand notation (<group id>:<artifact id>:<version>).
	 * @param classLoader       a class loader which {@code Dependency} will be injected into.
	 *
	 * @throws NullPointerException     if the given {@code shorthandNotation} or the given {@code classLoader} is {@code null}.
	 * @throws RuntimeException         if the error occurred while trying to download or inject {@code Dependency}.
	 * @throws IllegalArgumentException if the {@code shorthandNotation} does not have group id, artifact id or version.
	 */
	public static void injectDependency(String shorthandNotation, Repository repository, ClassLoader classLoader)
	{
		injectDependency(new Dependency(shorthandNotation), classLoader);
	}

	/**
	 * Injects {@code Dependency} with the given {@code groupId}
	 * and the given {@code artifactId} and the given {@code version}
	 * from the given {@code repository} into the given {@code classLoader}.
	 *
	 * @param groupId     an group id of the {@code Dependency}
	 * @param artifactId  an artifact id of the {@code Dependency}
	 * @param version     a version of the {@code Dependency}
	 * @param repository  a repository which holds dependency with the given {@code groupId},
	 *                    the given {@code artifactId} and the given {@code version}.
	 * @param classLoader a class loader which {@code Dependency}
	 *                    will be injected into.
	 *
	 * @throws NullPointerException if the given {@code groupId} or the given {@code artifactId} or the given
	 *                              {@code version} or the given {@code classLoader} is {@code null}.
	 * @throws RuntimeException     if the error occurred while trying to download or inject {@code Dependency}.
	 */
	public static void injectDependency(String groupId, String artifactId, String version, Repository repository, ClassLoader classLoader)
	{
		injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
	}

	/**
	 * Injects the given {@code depedency} into the given {@code classLoader}.
	 *
	 * @param dependency  a {@code Dependency} to inject.
	 * @param classLoader a class loader which the given
	 *                    {@code dependency} will be injected into.
	 *
	 * @throws NullPointerException     if the given {@code dependency} or the given {@code classLoader} is {@code null}.
	 * @throws RuntimeException         if the error occurred while trying to download or inject {@code depedency}.
	 * @throws IllegalArgumentException if shorthand notation was used to create an instance of {@code Dependency}
	 *                                  and the shorthand notation does not have group id, artifact id or version.
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
		if (! dependencyDestination.exists())
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
				throw new RuntimeException("Could not download dependency '" + dependencyName + "'", exception);
			}
		}
		if (! dependencyDestination.exists())
		{
			throw new RuntimeException("Could not download dependency '" + dependencyName + "'");
		}
		try
		{
			ADD_URL_METHOD.invoke(classLoader, dependencyDestination.toURI().toURL());
		}
		catch (Exception exception)
		{
			throw new RuntimeException("Could not inject dependency '" + dependencyName + "'", exception);
		}
	}
}