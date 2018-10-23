/*
 * This file is a part of dinjector, licensed under the MIT License.
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
package co.jaqobb.dinjector.dependency;

import co.jaqobb.dinjector.exception.MissingShorthandNotationInfoException;
import co.jaqobb.dinjector.repository.Repositories;
import co.jaqobb.dinjector.repository.Repository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * A dependency.
 */
public final class Dependency {
  /**
   * Creates a dependency with shorthand notation.
   *
   * @param shorthandNotation the dependency in shorthand notation
   * @return the dependency
   */
  public static Dependency of(final @NotNull String shorthandNotation) {
    return of(shorthandNotation, Repositories.MAVEN_CENTRAL);
  }

  /**
   * Creates a dependency with shorthand notation and repository.
   *
   * @param shorthandNotation the dependency in shorthand notation
   * @param repository the repository to the dependency
   * @return the dependency
   */
  public static Dependency of(final @NotNull String shorthandNotation, final @NotNull String repository) {
    return of(shorthandNotation, Repository.of(repository));
  }

  /**
   * Creates a dependency with shorthand notation and repository.
   *
   * @param shorthandNotation the dependency in shorthand notation
   * @param repository the repository to the dependency
   * @return the dependency
   * @throws MissingShorthandNotationInfoException if the shorthand notation is missing some info
   */
  public static Dependency of(final @NotNull String shorthandNotation, final @NotNull Repository repository) {
    final String[] data = shorthandNotation.split(":");
    if(data.length != 3) {
      throw new MissingShorthandNotationInfoException("Shorthand notation must have only group id, artifact id and version separated with ':'");
    }
    return of(data[0], data[1], data[2], repository);
  }

  /**
   * Creates a dependency with group id, artifact id, and version.
   *
   * @param groupId the dependency group id
   * @param artifactId the dependency artifact id
   * @param version the dependency version
   * @return the dependency
   */
  public static Dependency of(final @NotNull String groupId, final @NotNull String artifactId, final @NotNull String version) {
    return of(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
  }

  /**
   * Creates a dependency with group id, artifact id, version, and repository.
   *
   * @param groupId the dependency group id
   * @param artifactId the dependency artifact id
   * @param version the dependency version
   * @param repository the repository to the dependency
   * @return the dependency
   */
  public static Dependency of(final @NotNull String groupId, final @NotNull String artifactId, final @NotNull String version, final @NotNull String repository) {
    return of(groupId, artifactId, version, Repository.of(repository));
  }

  /**
   * Creates a dependency with group id, artifact id, version, and repository.
   *
   * @param groupId the dependency group id
   * @param artifactId the dependency artifact id
   * @param version the dependency version
   * @param repository the repository to the dependency
   * @return the dependency
   */
  public static Dependency of(final @NotNull String groupId, final @NotNull String artifactId, final @NotNull String version, final @NotNull Repository repository) {
    return new Dependency(groupId, artifactId, version, repository);
  }

  /**
   * The group id.
   */
  private final @NotNull String groupId;
  /**
   * The artifact id.
   */
  private final @NotNull String artifactId;
  /**
   * The version.
   */
  private final @NotNull String version;
  /**
   * The repository.
   */
  private final @NotNull Repository repository;

  private Dependency(final @NotNull String groupId, final @NotNull String artifactId, final @NotNull String version, final @NotNull Repository repository) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.repository = repository;
  }

  /**
   * Gets the dependency group id.
   *
   * @return the dependency group id
   */
  public @NotNull String getGroupId() {
    return this.groupId;
  }

  /**
   * Gets the dependency artifact id.
   *
   * @return the dependency artifact id
   */
  public @NotNull String getArtifactId() {
    return this.artifactId;
  }

  /**
   * Gets the dependency version.
   *
   * @return the dependency version
   */
  public @NotNull String getVersion() {
    return this.version;
  }

  /**
   * Gets the dependency repository.
   *
   * @return the dependency repository
   */
  public @NotNull Repository getRepository() {
    return this.repository;
  }

  /**
   * Gets the dependency download url.
   *
   * @return the dependency download url
   * @throws MalformedURLException if the dependency download url is malformed
   */
  public @NotNull URL getDownloadUrl() throws MalformedURLException {
    String url = this.repository.getUrl();
    if(!url.endsWith("/")) {
      url += "/";
    }
    return new URL(url + this.groupId.replace(".", "/") + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
  }

  @Override
  public boolean equals(final @Nullable Object object) {
    if(this == object) {
      return true;
    }
    if(object == null || this.getClass() != object.getClass()) {
      return false;
    }
    final Dependency that = (Dependency) object;
    return Objects.equals(this.groupId, that.groupId) && Objects.equals(this.artifactId, that.artifactId) && Objects.equals(this.version, that.version) && Objects.equals(this.repository, that.repository);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
  }
}