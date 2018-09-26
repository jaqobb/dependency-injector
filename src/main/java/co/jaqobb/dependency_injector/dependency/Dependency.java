/*
 * Copyright (c) Jakub Zagórski (jaqobb). All rights reserved.
 * Licensed under the MIT license.
 */
package co.jaqobb.dependency_injector.dependency;

import co.jaqobb.dependency_injector.exception.MissingShorthandNotationInfoException;
import co.jaqobb.dependency_injector.repository.Repositories;
import co.jaqobb.dependency_injector.repository.Repository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public final class Dependency {
    public static Dependency of(String shorthandNotation) {
        return of(shorthandNotation, Repositories.MAVEN_CENTRAL);
    }

    public static Dependency of(String shorthandNotation, String repository) {
        return of(shorthandNotation, Repository.of(repository));
    }

    public static Dependency of(String shorthandNotation, Repository repository) {
        Objects.requireNonNull(shorthandNotation, "shorthandNotation");
        Objects.requireNonNull(repository, "repository");
        String[] data = shorthandNotation.split(":");
        if (data.length != 3) {
            throw new MissingShorthandNotationInfoException("Shorthand notation must have only group id, artifact id and version separated with ':'");
        }
        String groupId = data[0];
        String artifactId = data[1];
        String version = data[2];
        return of(groupId, artifactId, version, repository);
    }

    public static Dependency of(String groupId, String artifactId, String version) {
        return of(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
    }

    public static Dependency of(String groupId, String artifactId, String version, String repository) {
        return of(groupId, artifactId, version, Repository.of(repository));
    }

    public static Dependency of(String groupId, String artifactId, String version, Repository repository) {
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(repository, "repository");
        return new Dependency(groupId, artifactId, version, repository);
    }

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final Repository repository;

    private Dependency(String groupId, String artifactId, String version, Repository repository) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.repository = repository;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getVersion() {
        return version;
    }

    public Repository getRepository() {
        return repository;
    }

    public URL getDownloadUrl() throws MalformedURLException {
        String url = repository.getUrl();
        if (!url.endsWith("/")) {
            url += "/";
        }
        return new URL(url + groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Dependency that = (Dependency) object;
        return Objects.equals(groupId, that.groupId) && Objects.equals(artifactId, that.artifactId) && Objects.equals(version, that.version) && Objects.equals(repository, that.repository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId, version, repository);
    }

    @Override
    public String toString() {
        return "Dependency]" + "groupId=" + groupId + ", artifactId=" + artifactId + ", version=" + version + ", repository=" + repository + "]";
    }
}