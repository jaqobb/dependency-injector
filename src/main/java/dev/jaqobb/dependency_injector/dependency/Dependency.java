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
package dev.jaqobb.dependency_injector.dependency;

import dev.jaqobb.dependency_injector.repository.Repositories;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public final class Dependency {
  private final String groupId;
  private final String artifactId;
  private final String version;
  private final String repository;

  public Dependency(String groupId, String artifactId, String version) {
    this(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
  }

  public Dependency(String groupId, String artifactId, String version, String repository) {
    this.groupId = Objects.requireNonNull(groupId, "groupId");
    this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
    this.version = Objects.requireNonNull(version, "version");
    this.repository = Objects.requireNonNull(repository, "repository");
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

  public String getRepository() {
    return repository;
  }

  public URL getDownloadUrl() throws MalformedURLException {
    String url = repository;
    if(!url.endsWith("/")) {
      url += "/";
    }
    return new URL(url + groupId.replace(".", "/") + "/" + artifactId + "/" + version + "/" + artifactId + "-" + version + ".jar");
  }

  @Override
  public boolean equals(Object object) {
    if(this == object) {
      return true;
    }
    if(object == null || getClass() != object.getClass()) {
      return false;
    }
    Dependency that = (Dependency) object;
    return Objects.equals(groupId, that.groupId) &&
      Objects.equals(artifactId, that.artifactId) &&
      Objects.equals(version, that.version) &&
      Objects.equals(repository, that.repository);
  }

  @Override
  public int hashCode() {
    return Objects.hash(groupId, artifactId, version, repository);
  }

  @Override
  public String toString() {
    return "Dependency{" +
      "groupId='" + groupId + "'" +
      ", artifactId='" + artifactId + "'" +
      ", version='" + version + "'" +
      ", repository=" + repository +
      "}";
  }
}