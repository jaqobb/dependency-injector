/*
 * This file is a part of DependencyInjector, licensed under the MIT License (MIT).
 * See the LICENSE.txt file at the root of this project for more details.
 */
package co.jaqobb.dependencyinjector;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.Objects;

import co.jaqobb.dependencyinjector.dependency.Dependency;
import co.jaqobb.dependencyinjector.exception.DependencyDownloadException;
import co.jaqobb.dependencyinjector.exception.DependencyInjectException;
import co.jaqobb.dependencyinjector.exception.MissingShorthandNotationInfoException;
import co.jaqobb.dependencyinjector.repository.Repository;

// Class that is being used to inject dependencies.
public final class DependencyInjector {

    // The cached addURL method.
    private static final Method ADD_URL_METHOD;

    /**
     * Caches addURL method.
     *
     * @throws InternalError If the error occurred while trying to cache addURL method
     */
    static {
        try {
            ADD_URL_METHOD = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            ADD_URL_METHOD.setAccessible(true);
        } catch (NoSuchMethodException exception) {
            throw new InternalError("Unable to cache add url method", exception);
        }
    }

    // Suppress default constructor to ensure non-instantiability.
    private DependencyInjector() {
    }

    /**
     * Injects the given array of {@link Dependency} into the given {@link ClassLoader}.
     *
     * @param dependencies The array of {@link Dependency} to inject
     * @param classLoader The {@link ClassLoader} into which all {@link Dependency} will be injected
     *
     * @throws NullPointerException If the given array of {@link Dependency} or any {@link Dependency} or {@link ClassLoader} is {@code
     * null}
     * @throws DependencyDownloadException If the error occurred while trying to download any {@link Dependency}
     * @throws DependencyInjectException If the error occurred while trying inject any {@link Dependency}
     */
    public static void injectDependencies(Dependency[] dependencies, ClassLoader classLoader) {
        Objects.requireNonNull(dependencies, "dependencies");

        for (Dependency dependency : dependencies) {
            DependencyInjector.injectDependency(dependency, classLoader);
        }
    }

    /**
     * Injects the {@link Dependency} from the Maven central repository using the given shorthand notation into the given {@link
     * ClassLoader}.
     *
     * @param shorthandNotation The shorthand notation (group id:artifact id:version)
     * @param classLoader The {@link ClassLoader} into which the {@link Dependency} will be injected
     *
     * @throws NullPointerException If the given shorthand notation or {@link ClassLoader} is {@code null}
     * @throws DependencyDownloadException If the error occurred while trying to download the {@link Dependency}
     * @throws DependencyInjectException If the error occurred while trying inject the {@link Dependency}
     * @throws MissingShorthandNotationInfoException If the shorthand notation is missing group id, artifact id, or version
     */
    public static void injectDependency(String shorthandNotation, ClassLoader classLoader) {
        DependencyInjector.injectDependency(new Dependency(shorthandNotation), classLoader);
    }

    /**
     * Injects the {@link Dependency} with the given group id, artifact id, and version from the Maven central repository into the given
     * {@link ClassLoader}.
     *
     * @param groupId The group id of the {@link Dependency}
     * @param artifactId The artifact id of the {@link Dependency}
     * @param version The version of the {@link Dependency}
     * @param classLoader The {@link ClassLoader} into which the {@link Dependency} will be injected
     *
     * @throws NullPointerException If the given group id, artifact id, version or {@link ClassLoader} is {@code null}
     * @throws DependencyDownloadException If the error occurred while trying to download the {@link Dependency}
     * @throws DependencyInjectException If the error occurred while trying inject the {@link Dependency}
     */
    public static void injectDependency(String groupId, String artifactId, String version, ClassLoader classLoader) {
        DependencyInjector.injectDependency(new Dependency(groupId, artifactId, version), classLoader);
    }

    /**
     * Injects the {@link Dependency} from the given repository using the given shorthand notation into the given {@link ClassLoader}.
     *
     * @param shorthandNotation The shorthand notation (group id:artifact id:version)
     * @param classLoader The {@link ClassLoader} into which the {@link Dependency} will be injected
     *
     * @throws NullPointerException If the given shorthand notation or {@link ClassLoader} is {@code null}
     * @throws DependencyDownloadException If the error occurred while trying to download the {@link Dependency}
     * @throws DependencyInjectException If the error occurred while trying inject the {@link Dependency}
     * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id, or version
     */
    public static void injectDependency(String shorthandNotation, String repository, ClassLoader classLoader) {
        DependencyInjector.injectDependency(new Dependency(shorthandNotation, repository), classLoader);
    }

    /**
     * Injects the {@link Dependency} from the given {@link Repository} using the given shorthand notation into the given {@link
     * ClassLoader}.
     *
     * @param shorthandNotation The shorthand notation (group id:artifact id:version)
     * @param classLoader The {@link ClassLoader} into which the {@link Dependency} will be injected
     *
     * @throws NullPointerException If the given shorthand notation, {@link Repository}, or {@link ClassLoader} is {@code null}
     * @throws DependencyDownloadException If the error occurred while trying to download the {@link Dependency}
     * @throws DependencyInjectException If the error occurred while trying inject the {@link Dependency}
     * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id, or version
     */
    public static void injectDependency(String shorthandNotation, Repository repository, ClassLoader classLoader) {
        DependencyInjector.injectDependency(new Dependency(shorthandNotation, repository), classLoader);
    }

    /**
     * Injects the {@link Dependency} with the given group id, artifact id, and version from the given repository into the given {@link
     * ClassLoader}.
     *
     * @param groupId The group id of the {@link Dependency}
     * @param artifactId The artifact id of the {@link Dependency}
     * @param version The version of the {@link Dependency}
     * @param repository The repository which holds the {@link Dependency} with the given group id, artifact id, and version
     * @param classLoader The {@link ClassLoader} into which the {@link Dependency} will be injected
     *
     * @throws NullPointerException If the given group id, artifact id, version, repository, or {@link ClassLoader} is {@code null}
     * @throws DependencyDownloadException If the error occurred while trying to download the {@link Dependency}
     * @throws DependencyInjectException If the error occurred while trying inject the {@link Dependency}
     */
    public static void injectDependency(String groupId, String artifactId, String version, String repository, ClassLoader classLoader) {
        DependencyInjector.injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
    }

    /**
     * Injects the {@link Dependency} with the given group id, artifact id, and version from the given {@link Repository} into the given
     * {@link ClassLoader}.
     *
     * @param groupId The group id of the {@link Dependency}
     * @param artifactId The artifact id of the {@link Dependency}
     * @param version The version of the {@link Dependency}
     * @param repository The {@link Repository} which holds the {@link Dependency} with the given group id, artifact id, and version
     * @param classLoader The {@link ClassLoader} into which the {@link Dependency} will be injected
     *
     * @throws NullPointerException If the given group id, artifact id, version, repository, or {@link ClassLoader} is {@code null}
     * @throws DependencyDownloadException If the error occurred while trying to download the {@link Dependency}
     * @throws DependencyInjectException If the error occurred while trying inject the {@link Dependency}
     */
    public static void injectDependency(String groupId, String artifactId, String version, Repository repository, ClassLoader classLoader) {
        DependencyInjector.injectDependency(new Dependency(groupId, artifactId, version, repository), classLoader);
    }

    /**
     * Injects the given {@link Dependency} into the given {@link ClassLoader}.
     *
     * @param dependency The {@link Dependency} to inject
     * @param classLoader The {@link ClassLoader} into which the given {@link Dependency} will be injected
     *
     * @throws NullPointerException If the given {@link Dependency} or {@link ClassLoader} is {@code null}
     * @throws DependencyDownloadException If the error occurred while trying to download the given {@link Dependency}
     * @throws DependencyInjectException If the error occurred while trying inject the given {@link Dependency}
     */
    public static void injectDependency(Dependency dependency, ClassLoader classLoader) {
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

        if (!dependencyDestination.exists()) {
            try {
                URL url = dependency.getDownloadUrl();

                try (InputStream inputStream = url.openStream()) {
                    Files.copy(inputStream, dependencyDestination.toPath());
                }
            } catch (Exception exception) {
                throw new DependencyDownloadException(String.format("Unable to download dependency '%s'", dependencyName), exception);
            }
        }

        if (!dependencyDestination.exists()) {
            throw new DependencyDownloadException(String.format("Unable to download dependency '%s'", dependencyName));
        }

        try {
            ADD_URL_METHOD.invoke(classLoader, dependencyDestination.toURI().toURL());
        } catch (Exception exception) {
            throw new DependencyInjectException(String.format("Unable to inject dependency '%s'", dependencyName), exception);
        }
    }
}