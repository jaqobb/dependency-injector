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

import co.jaqobb.dependency.injector.exception.MissingShorthandNotationInfoException;
import co.jaqobb.dependency.injector.repository.Repositories;
import co.jaqobb.dependency.injector.repository.Repository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

/**
 * Class that holds dependency info like group id, artifact id etc.
 */
public final class Dependency
{

	/**
	 * Group id of the dependency
	 */
	private final String     groupId;
	/**
	 * Artifact id of the dependency.
	 */
	private final String     artifactId;
	/**
	 * Version of the dependency.
	 */
	private final String     version;
	/**
	 * Repository of the dependency.
	 */
	private final Repository repository;

	/**
	 * Constructs new dependency instance with the given shorthand notation and maven central repository.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 * @throws NullPointerException If the given shorthand notation is null.
	 * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version.
	 */
	public Dependency(String shorthandNotation)
	{
		this(shorthandNotation, Repositories.MAVEN_CENTRAL);
	}

	/**
	 * Constructs new dependency instance with, the given shorthand notation and the given repository.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 * @param repository A repository which holds dependency with the given group id, arifact id and version.
	 * @throws NullPointerException If the given shorthand notation or the given repository is null.
	 * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version.
	 */
	public Dependency(String shorthandNotation, String repository)
	{
		Objects.requireNonNull(shorthandNotation, "shorthandNotation");
		Objects.requireNonNull(repository, "repository");
		String[] data = shorthandNotation.split(":");
		if (data.length != 3)
		{
			throw new MissingShorthandNotationInfoException("Shorthand notation must have only group id, artifact id and version separated by ':'");
		}
		this.groupId = data[0];
		this.artifactId = data[1];
		this.version = data[2];
		this.repository = new Repository(repository);
	}

	/**
	 * Constructs new dependency instance with, the given shorthand notation and the given repository.
	 *
	 * @param shorthandNotation A shorthand notation (<group id>:<artifact id>:<version>).
	 * @param repository A repository which holds dependency with the given group id, arifact id and version.
	 * @throws NullPointerException If the given shorthand notation or the given repository is null.
	 * @throws MissingShorthandNotationInfoException If the given shorthand notation is missing group id, artifact id or version.
	 */
	public Dependency(String shorthandNotation, Repository repository)
	{
		Objects.requireNonNull(shorthandNotation, "shorthandNotation");
		Objects.requireNonNull(repository, "repository");
		String[] data = shorthandNotation.split(":");
		if (data.length != 3)
		{
			throw new MissingShorthandNotationInfoException("Shorthand notation must have only group id, artifact id and version separated by ':'");
		}
		this.groupId = data[0];
		this.artifactId = data[1];
		this.version = data[2];
		this.repository = repository;
	}

	/**
	 * Constructs new dependency instance with, the given group id, artfact id, version and maven central repository.
	 *
	 * @param groupId A group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the dependency.
	 * @throws NullPointerException If the given group id, artifact id or version is null.
	 */
	public Dependency(String groupId, String artifactId, String version)
	{
		this(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
	}

	/**
	 * Constructs new dependency instance with, the given group id, artifact id, version and repository.
	 *
	 * @param groupId A group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the dependency.
	 * @param repository A repository which holds dependency with the given group id, artifact id and version.
	 * @throws NullPointerException If the given group id, artifact id, version or repository is null.
	 */
	public Dependency(String groupId, String artifactId, String version, String repository)
	{
		this.groupId = Objects.requireNonNull(groupId, "groupId");
		this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
		this.version = Objects.requireNonNull(version, "version");
		this.repository = new Repository(Objects.requireNonNull(repository, "repository"));
	}

	/**
	 * Constructs new dependency instance with, the given group id, artifact id, version and repository.
	 *
	 * @param groupId A group id of the dependency.
	 * @param artifactId An artifact id of the dependency.
	 * @param version A version of the dependency.
	 * @param repository A repository which holds dependency with the given group id, artifact id and version.
	 * @throws NullPointerException If the given group id, artifact id, version or repository is null.
	 */
	public Dependency(String groupId, String artifactId, String version, Repository repository)
	{
		this.groupId = Objects.requireNonNull(groupId, "groupId");
		this.artifactId = Objects.requireNonNull(artifactId, "artifactId");
		this.version = Objects.requireNonNull(version, "version");
		this.repository = Objects.requireNonNull(repository, "repository");
	}

	/**
	 * Returns a group id of this dependency.
	 *
	 * @return A group id of this dependency.
	 */
	public String getGroupId()
	{
		return this.groupId;
	}

	/**
	 * Returns an artifact id of this dependency.
	 *
	 * @return An artifact id of this deependency.
	 */
	public String getArtifactId()
	{
		return this.artifactId;
	}

	/**
	 * Returns a version id of this dependency.
	 *
	 * @return A version id of this dependency.
	 */
	public String getVersion()
	{
		return this.version;
	}

	/**
	 * Returns a repository id of this dependency.
	 *
	 * @return A repository id of this dependency.
	 */
	public Repository getRepository()
	{
		return this.repository;
	}

	/**
	 * Returns a download url id of this dependency.
	 *
	 * @return A download url id of this dependency.
	 */
	public URL getDownloadUrl() throws MalformedURLException
	{
		String url = this.repository.getUrl();
		if (!url.endsWith("/"))
		{
			url += "/";
		}
		String groupId = this.groupId.replace(".", "/");
		return new URL(url + groupId + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
	}

	/**
	 * Returns true if the given object is the same as this class and false otherwise.
	 *
	 * @param object An object to check.
	 * @return True if the given object is the same as this class and false otherwise.
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
	 * @return A hash code of this class.
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
	}

	/**
	 * Returns a nice looking representation of this class.
	 *
	 * @return A nice looking representation of this class.
	 */
	@Override
	public String toString()
	{
		return "Dependency{" + "groupId=" + this.groupId + ", artifactId=" + this.artifactId + ", version=" + this.version + ", repository=" + this.repository + "}";
	}

}