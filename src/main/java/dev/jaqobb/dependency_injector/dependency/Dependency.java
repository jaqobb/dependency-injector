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

  public Dependency(final String groupId, final String artifactId, final String version) {
    this(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
  }

  public Dependency(final String groupId, final String artifactId, final String version, final String repository) {
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

  public String getRepository() {
    return this.repository;
  }

  public URL getDownloadUrl() throws MalformedURLException {
    String url = this.repository;
    if(!url.endsWith("/")) {
      url += "/";
    }
    return new URL(url + this.groupId.replace(".", "/") + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
  }

  @Override
  public boolean equals(final Object object) {
    if(this == object) {
      return true;
    }
    if(object == null || this.getClass() != object.getClass()) {
      return false;
    }
    final Dependency that = (Dependency) object;
    return Objects.equals(this.groupId, that.groupId) &&
      Objects.equals(this.artifactId, that.artifactId) &&
      Objects.equals(this.version, that.version) &&
      Objects.equals(this.repository, that.repository);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
  }

  @Override
  public String toString() {
    return "Dependency{" +
      "groupId='" + this.groupId + "'" +
      ", artifactId='" + this.artifactId + "'" +
      ", version='" + this.version + "'" +
      ", repository=" + this.repository +
      "}";
  }
}