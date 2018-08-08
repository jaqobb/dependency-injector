/*
 * This file is a part of DependencyInjector, licensed under the MIT License (MIT).
 * See the LICENSE.txt file at the root of this project for more details.
 */
package co.jaqobb.dependencyinjector.repository;

import java.util.Objects;

// The {@code Repository} data class.
public final class Repository {

    // The url of the {@code Repository}
    private final String url;

    /**
     * Constructs a new {@code Repository} object with the given url.
     *
     * @param url The url
     *
     * @throws NullPointerException If the given url is {@code null}
     */
    public Repository(String url) {
        this.url = Objects.requireNonNull(url, "url");
    }

    /**
     * Gets this {@code Repository} url.
     *
     * @return This {@code Repository} url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Checks if the given object is the same as this class.
     *
     * @param object The object to check
     *
     * @return {@code true} if the given object is the same as this class and {@code false} otherwise
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
     * @return A hash code of this class
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.url);
    }

    /**
     * Returns a better looking representation of this class.
     *
     * @return A better looking representation of this class
     */
    @Override
    public String toString() {
        return "Repository{" + "url=" + this.url + "}";
    }
}