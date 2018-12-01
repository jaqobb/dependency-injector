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
    if(shorthandNotation == null) {
      throw new NullPointerException("shorthandNotation cannot be null");
    }
    if(repository == null) {
      throw new NullPointerException("repository cannot be null");
    }
    String[] data = shorthandNotation.split(":");
    if(data.length != 3) {
      throw new MissingShorthandNotationInfoException("Shorthand notation must have only group id, artifact id and version separated with ':'");
    }
    return of(data[0], data[1], data[2], repository);
  }

  public static Dependency of(String groupId, String artifactId, String version) {
    return of(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
  }

  public static Dependency of(String groupId, String artifactId, String version, String repository) {
    return of(groupId, artifactId, version, Repository.of(repository));
  }

  public static Dependency of(String groupId, String artifactId, String version, Repository repository) {
    if(groupId == null) {
      throw new NullPointerException("groupId cannot be null");
    }
    if(artifactId == null) {
      throw new NullPointerException("artifactId cannot be null");
    }
    if(version == null) {
      throw new NullPointerException("version cannot be null");
    }
    if(repository == null) {
      throw new NullPointerException("repository cannot be null");
    }
    return new Dependency(groupId, artifactId, version, repository);
  }

  private String groupId;
  private String artifactId;
  private String version;
  private Repository repository;

  private Dependency(String groupId, String artifactId, String version, Repository repository) {
    this.groupId = groupId;
    this.artifactId = artifactId;
    this.version = version;
    this.repository = repository;
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

  public URL getDownloadURL() throws MalformedURLException {
    String url = this.repository.getURL();
    if(!url.endsWith("/")) {
      url += "/";
    }
    return new URL(url + this.groupId.replace(".", "/") + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
  }

  @Override
  public boolean equals(Object object) {
    if(this == object) {
      return true;
    }
    if(object == null || this.getClass() != object.getClass()) {
      return false;
    }
    Dependency that = (Dependency) object;
    return Objects.equals(this.groupId, that.groupId) && Objects.equals(this.artifactId, that.artifactId) && Objects.equals(this.version, that.version) && Objects.equals(this.repository, that.repository);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
  }
}