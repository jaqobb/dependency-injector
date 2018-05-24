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

package co.jaqobb.dependency.injector.dependency;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import co.jaqobb.dependency.injector.repository.Repositories;
import co.jaqobb.dependency.injector.repository.Repository;

/**
 * Class that holds dependency info
 * like group id, artifact id etc.
 */
public final class Dependency
{
	/**
	 * Group id of the {@code Dependency}.
	 */
	private final String     groupId;
	/**
	 * Artifact id of the {@code Dependency}.
	 */
	private final String     artifactId;
	/**
	 * Version of the {@code Dependency}.
	 */
	private final String     version;
	/**
	 * Repository of the {@code Dependency}.
	 */
	private final Repository repository;

	/**
	 * Constructs new {@code Dependency} instance with the given
	 * {@code shorthandNotation} and maven central repository.
	 *
	 * @param shorthandNotation a shorthand notation (<group id>:<artifact id>:<version>).
	 *
	 * @throws NullPointerException     if the given {@code shorthandNotation} is {@code null}.
	 * @throws IllegalArgumentException if the {@code shorthandNotation} does not have group id,
	 *                                  artifact id and version.
	 */
	public Dependency(String shorthandNotation)
	{
		this(shorthandNotation, Repositories.MAVEN_CENTRAL);
	}

	/**
	 * Constructs new {@code Dependency} instance with,
	 * the given {@code shorthandNotation} and the given
	 * {@code repository}.
	 *
	 * @param shorthandNotation a shorthand notation (<group id>:<artifact id>:<version>).
	 * @param repository        a repository which holds dependency with the
	 *                          given {@code groupId}, {@code artifactId},
	 *                          and {@code version}.
	 *
	 * @throws NullPointerException     if the given {@code shorthandNotation} or
	 *                                  the given {@code repository} is {@code null}.
	 * @throws IllegalArgumentException if the {@code shorthandNotation} does not
	 *                                  have group id, artifact id and version.
	 */
	public Dependency(String shorthandNotation, Repository repository)
	{
		Objects.requireNonNull(shorthandNotation, "shorthandNotation");
		Objects.requireNonNull(repository, "repository");
		String[] data = shorthandNotation.split(":");
		if (data.length != 3)
		{
			throw new IllegalArgumentException("shorthandNotation must have group id, artifact id and version separated with ':'");
		}
		this.groupId = data[0];
		this.artifactId = data[1];
		this.version = data[2];
		this.repository = repository;
	}

	/**
	 * Constructs new {@code Dependency} instance with,
	 * the given {@code groupId}, {@code artifactId},
	 * {@code version} and maven central repository.
	 *
	 * @param groupId    a group id of the dependency.
	 * @param artifactId an artifact id of the dependency.
	 * @param version    a version of the dependency.
	 *
	 * @throws NullPointerException if the given {@code groupId} or the given {@code artifactId}
	 *                              or the given {@code version} is {@code null}.
	 * @see Repositories
	 */
	public Dependency(String groupId, String artifactId, String version)
	{
		this(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
	}

	/**
	 * Constructs new {@code Dependency} instance with,
	 * the given {@code groupId}, {@code artifactId},
	 * {@code version} and {@code repository}.
	 *
	 * @param groupId    a group id of the dependency.
	 * @param artifactId an artifact id of the dependency.
	 * @param version    a version of the dependency.
	 * @param repository a repository which holds dependency with the
	 *                   given {@code groupId}, {@code artifactId},
	 *                   and {@code version}.
	 *
	 * @throws NullPointerException if the given {@code groupId} or the given {@code artifactId}
	 *                              or the given {@code version} or the given {@code repository}
	 *                              is {@code null}.
	 */
	public Dependency(String groupId, String artifactId, String version, Repository repository)
	{
		this.groupId = Objects.requireNonNull(groupId, "groupId");
		this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
		this.version = Objects.requireNonNull(version, "version");
		this.repository = Objects.requireNonNull(repository, "repository");
	}

	/**
	 * Returns a group id of this {@code Dependency}.
	 *
	 * @return a group id of this {@code Dependency}.
	 */
	public String getGroupId()
	{
		return this.groupId;
	}

	/**
	 * Returns an artifact id of this {@code Dependency}.
	 *
	 * @return an artifact id of this {@code Dependency}.
	 */
	public String getArtifactId()
	{
		return this.artifactId;
	}

	/**
	 * Returns a version id of this {@code Dependency}.
	 *
	 * @return a version id of this {@code Dependency}.
	 */
	public String getVersion()
	{
		return this.version;
	}

	/**
	 * Returns a repository id of this {@code Dependency}.
	 *
	 * @return a repository id of this {@code Dependency}.
	 */
	public Repository getRepository()
	{
		return this.repository;
	}

	/**
	 * Returns a download url id of this {@code Dependency}.
	 *
	 * @return a download url id of this {@code Dependency}.
	 */
	public URL getDownloadUrl() throws MalformedURLException
	{
		String url = this.repository.getUrl();
		if (! url.endsWith("/"))
		{
			url += "/";
		}
		String groupId = this.groupId.replace(".", "/");
		return new URL(url + groupId + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
	}

	/**
	 * Checks if the given {@code object}
	 * is the same as this class.
	 *
	 * @param object an object to be checked.
	 *
	 * @return {@code true} if both objects
	 * are the same, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object object)
	{
		if (this == object)
		{
			return true;
		}
		if (object == null || this.getClass() != object.getClass())
		{
			return false;
		}
		Dependency that = (Dependency) object;
		return Objects.equals(this.groupId, that.groupId) && Objects.equals(this.artifactId, that.artifactId) && Objects.equals(this.version, that.version) && Objects.equals(this.repository, that.repository);
	}

	/**
	 * Returns a hash code of this class.
	 *
	 * @return a hash code of this class.
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
	}

	/**
	 * Returns a nice looking representation of this class.
	 *
	 * @return a nice looking representation of this class.
	 */
	@Override
	public String toString()
	{
		return "Dependency{" + "groupId=" + this.groupId + ", artifactId=" + this.artifactId + ", version=" + this.version + ", repository=" + this.repository + "}";
	}
}