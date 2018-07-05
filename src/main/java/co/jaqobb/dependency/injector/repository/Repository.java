package co.jaqobb.dependency.injector.repository;

import java.util.Objects;

/**
 * Class that represents a repository.
 */
public final class Repository {
	/**
	 * Url of the repository.
	 */
	private final String url;

	/**
	 * Constructs a new Repository class instance with the given url.
	 *
	 * @param url A url.
	 *
	 * @throws NullPointerException If the given url is null.
	 */
	public Repository(String url) {
		this.url = Objects.requireNonNull(url, "url");
	}

	/**
	 * Returns this repository url.
	 *
	 * @return This repository url.
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Returns true if the given object is the same as this class, and false otherwise.
	 *
	 * @param object An object to check.
	 *
	 * @return True if the given object is the same as this class, and false otherwise.
	 */
	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null || this.getClass() != object.getClass()) {
			return false;
		}
		Repository that = (Repository) object;
		return Objects.equals(this.url, that.url);
	}

	/**
	 * Returns a hash code of this class.
	 *
	 * @return A hash code of this class.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.url);
	}

	/**
	 * Returns a nice looking representation of this class.
	 *
	 * @return A nice looking representation of this class.
	 */
	@Override
	public String toString() {
		return "Repository{" + "url=" + this.url + "}";
	}
}