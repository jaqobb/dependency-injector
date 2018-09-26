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
import co.jaqobb.dependency_injector.exception.DependencyDownloadException;
import co.jaqobb.dependency_injector.exception.DependencyInjectException;
import co.jaqobb.dependency_injector.repository.Repository;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Objects;

public final class DependencyInjector
{
    private static final Method ADD_URL_METHOD;

    static
    {
        try
        {
            ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            ADD_URL_METHOD.setAccessible(true);
        }
        catch (NoSuchMethodException exception)
        {
            throw new InternalError("Unable to cache add url method", exception);
        }
    }

    private DependencyInjector()
    {
    }

    public static void injectDependencies(Dependency[] dependencies, ClassLoader classLoader)
    {
        Objects.requireNonNull(dependencies, "dependencies");
        for (Dependency dependency : dependencies)
        {
            injectDependency(dependency, classLoader);
        }
    }

    public static void injectDependency(String shorthandNotation, ClassLoader classLoader)
    {
        injectDependency(new Dependency(shorthandNotation), classLoader);
    }

    public static void injectDependency(String groupId, String artifactId, String version, ClassLoader classLoader)
    {
        injectDependency(new Dependency(groupId, artifactId, version), classLoader);
    }

    public static void injectDependency(String shorthandNotation, String repository, ClassLoader classLoader)
    {
        injectDependency(new Dependency(shorthandNotation, repository), classLoader);
    }

    public static void injectDependency(String shorthandNotation, Repository repository, ClassLoader classLoader)
    {
        injectDependency(new Dependency(shorthandNotation, repository), classLoader);
    }

    public static void injectDependency(String groupId, String artifactId, String version, String repository, ClassLoader classLoader)
    {
        injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
    }

    public static void injectDependency(String groupId, String artifactId, String version, Repository repository, ClassLoader classLoader)
    {
        injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
    }

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
                throw new DependencyDownloadException("Unable to download dependency '" + dependencyName + "'", exception);
            }
        }
        if (!dependencyDestination.exists())
        {
            throw new DependencyDownloadException("Unable to download dependency '" + dependencyName + "'");
        }
        try
        {
            ADD_URL_METHOD.invoke(classLoader, dependencyDestination.toURI().toURL());
        }
        catch (Exception exception)
        {
            throw new DependencyInjectException("Unable to inject dependency '" + dependencyName + "'", exception);
        }
    }
}