/*
 * This file is a part of DependencyInjector, licensed under the MIT License (MIT).
 * See the LICENSE.txt file at the root of this project for more details.
 */
package co.jaqobb.dependencyinjector.dependency;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import co.jaqobb.dependencyinjector.exception.MissingShorthandNotationInfoException;
import co.jaqobb.dependencyinjector.repository.Repositories;
import co.jaqobb.dependencyinjector.repository.Repository;

// The {@code Dependency} data class.
public final class Dependency {

    // The group id of the {@code Dependency}.
    private final String groupId;
    // The artifact id of the {@code Dependency}.
    private final String artifactId;
    // The version of the {@code Dependency}.
    private final String version;
    // Repository of the {@code Dependency}.
    private final Repository repository;

    /**
     * Constructs a new {@code Dependency} object with the given shorthand notation. Maven central repository will be used as a repository.
     *
     * @param shorthandNotation The shorthand notation (group id:artifact id:version)
     *
     * @throws NullPointerException If the given shorthand notation is {@code null}
     * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id, or version
     */
    public Dependency(String shorthandNotation) {
        this(shorthandNotation, Repositories.MAVEN_CENTRAL);
    }

    /**
     * Constructs a new {@code Dependency} object with the given shorthand notation and repository.
     *
     * @param shorthandNotation The shorthand notation (group id:artifact id:version)
     * @param repository The repository which holds the {@code Dependency} with the given group id, artifact id, and version
     *
     * @throws NullPointerException If the given shorthand notation or repository is {@code null}
     * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version
     */
    public Dependency(String shorthandNotation, String repository) {
        this(shorthandNotation, new Repository(repository));
    }

    /**
     * Constructs a new {@code Dependency} object with the given shorthand notation and repository.
     *
     * @param shorthandNotation The shorthand notation (group id:artifact id:version)
     * @param repository The repository which holds the {@code Dependency} with the given group id, artifact id, and version
     *
     * @throws NullPointerException If the given shorthand notation or repository is {@code null}
     * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id, or version
     */
    public Dependency(String shorthandNotation, Repository repository) {
        Objects.requireNonNull(shorthandNotation, "shorthandNotation");
        Objects.requireNonNull(repository, "repository");

        String[] data = shorthandNotation.split(":");

        if (data.length != 3) {
            throw new MissingShorthandNotationInfoException("Shorthand notation must have only group id, artifact id and version separated with ':'");
        }

        this.groupId = data[0];
        this.artifactId = data[1];
        this.version = data[2];
        this.repository = repository;
    }

    /**
     * Constructs a new {@code Dependency} object with the given group id, artifact id, and version. Maven central repository will be used
     * as a repository.
     *
     * @param groupId The group id of the {@code Dependency}
     * @param artifactId The artifact id of the {@code Dependency}
     * @param version The version of the {@code Dependency}
     *
     * @throws NullPointerException If the given group id, artifact id, or version is {@code null}
     */
    public Dependency(String groupId, String artifactId, String version) {
        this(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
    }

    /**
     * Constructs a new {@code Dependency} object with the given group id, artifact id, version, and repository.
     *
     * @param groupId A group id of the {@code Dependency}
     * @param artifactId An artifact id of the {@code Dependency}
     * @param version A version of the {@code Dependency}
     * @param repository A repository which holds {@code Dependency} with the given group id, artifact id, and version
     *
     * @throws NullPointerException If the given group id, artifact id, version or repository is {@code null}
     */
    public Dependency(String groupId, String artifactId, String version, String repository) {
        this(groupId, artifactId, version, new Repository(repository));
    }

    /**
     * Constructs a new {@code Dependency} object with the given group id, artifact id, version, and repository.
     *
     * @param groupId A group id of the {@code Dependency}
     * @param artifactId An artifact id of the {@code Dependency}
     * @param version A version of the {@code Dependency}
     * @param repository A repository which holds the {@code Dependency} with the given group id, artifact id, and version
     *
     * @throws NullPointerException If the given group id, artifact id, version or repository is {@code null}
     */
    public Dependency(String groupId, String artifactId, String version, Repository repository) {
        this.groupId = Objects.requireNonNull(groupId, "groupId");
        this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
        this.version = Objects.requireNonNull(version, "version");
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    /**
     * Gets this {@code Dependency} group id.
     *
     * @return This {@code Dependency} group id
     */
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * Gets this {@code Dependency} artifact id.
     *
     * @return This {@code Dependency} artifact id
     */
    public String getArtifactId() {
        return this.artifactId;
    }

    /**
     * Gets this {@code Dependency} version.
     *
     * @return This {@code Dependency} version
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * Gets this {@code Dependency} repository.
     *
     * @return This {@code Dependency} repository
     */
    public Repository getRepository() {
        return this.repository;
    }

    /**
     * Gets this {@code Dependency} download url.
     *
     * @return This {@code Dependency} download url
     */
    public URL getDownloadUrl() throws MalformedURLException {
        String url = this.repository.getUrl();

        if (!url.endsWith("/")) {
            url += "/";
        }

        String groupId = this.groupId.replace(".", "/");
        return new URL(url + groupId + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
    }

    /**
     * Checks if the given object is the same as this class.
     *
     * @param object The object to check
     *
     * @return {@code true} if the given object is the same as this class and {@code false} otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }

        Dependency that = (Dependency) object;
        return Objects.equals(this.groupId, that.groupId) && Objects.equals(this.artifactId, that.artifactId) && Objects.equals(this.version, that.version) && Objects.equals(this.repository, that.repository);
    }

    /**
     * Returns a hash code of this class.
     *
     * @return A hash code of this class
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
    }

    /**
     * Returns a better looking representation of this class.
     *
     * @return A better looking representation of this class
     */
    @Override
    public String toString() {
        return "Dependency{" + "groupId=" + this.groupId + ", artifactId=" + this.artifactId + ", version=" + this.version + ", repository=" + this.repository + "}";
    }

}