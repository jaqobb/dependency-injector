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

package dev.jaqobb.dependencyinjector.dependency;

import dev.jaqobb.dependencyinjector.repository.Repositories;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Dependency {

	@NotNull
	private String groupId;
	@NotNull
	private String artifactId;
	@NotNull
	private String version;
	@NotNull
	private String repository;

	public Dependency(@NotNull String groupId, @NotNull String artifactId, @NotNull String version) {
		this(groupId, artifactId, version, Repositories.MAVEN_CENTRAL);
	}

	public Dependency(@NotNull String groupId, @NotNull String artifactId, @NotNull String version, @NotNull String repository) {
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
		this.repository = repository;
	}

	@NotNull
	public String getGroupId() {
		return this.groupId;
	}

	@NotNull
	public String getArtifactId() {
		return this.artifactId;
	}

	@NotNull
	public String getVersion() {
		return this.version;
	}

	@NotNull
	public String getRepository() {
		return this.repository;
	}

	@NotNull
	public URL getDownloadUrl() throws MalformedURLException {
		String url = this.repository;
		if (!url.endsWith("/")) {
			url += "/";
		}
		return new URL(url + this.groupId.replace(".", "/") + "/" + this.artifactId + "/" + this.version + "/" + this.artifactId + "-" + this.version + ".jar");
	}

	@Override
	public boolean equals(@Nullable Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}
		Dependency that = (Dependency) object;
		return Objects.equals(this.groupId, that.groupId) &&
			Objects.equals(this.artifactId, that.artifactId) &&
			Objects.equals(this.version, that.version) &&
			Objects.equals(this.repository, that.repository);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.groupId, this.artifactId, this.version, this.repository);
	}
}
