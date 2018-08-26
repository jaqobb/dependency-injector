package co.jaqobb.dependencyinjector.dependency;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import co.jaqobb.dependencyinjector.exception.MissingShorthandNotationInfoException;
import co.jaqobb.dependencyinjector.repository.Repositories;
import co.jaqobb.dependencyinjector.repository.Repository;

public final class Dependency {

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final Repository repository;

    public Dependency(String shorthandNotation) {
        this(shorthandNotation, Repositories.MAVEN_CENTRAL);
    }

    public Dependency(String shorthandNotation, String repository) {
        this(shorthandNotation, new Repository(repository));
    }

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

    public Dependency(String groupId, String artifactId, String version) {
        this(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
    }

    public Dependency(String groupId, String artifactId, String version, String repository) {
        this(groupId, artifactId, version, new Repository(repository));
    }

    public Dependency(String groupId, String artifactId, String version, Repository repository) {
        this.groupId = Objects.requireNonNull(groupId, "groupId");
        this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
        this.version = Objects.requireNonNull(version, "version");
        this.repository = Objects.requireNonNull(repository, "repository");
    }

    public String getGroupId() {
        return this.groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public String getVersion() {
        return this.version;
    }

    public Repository getRepository() {
        return this.repository;
    }

    public URL getDownloadUrl() throws MalformedURLException {
        String url = this.repository.getUrl();

        if (!url.endsWith("/")) {
            url += "/";
        }

        String groupId = this.groupId.replace(".", "/");

        return new URL(url + groupId + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
    }

    @Override
    public String toString() {
        return "Dependency{" + "groupId=" + this.groupId + ", artifactId=" + this.artifactId + ", version=" + this.version + ", repository=" + this.repository + "}";
    }

}