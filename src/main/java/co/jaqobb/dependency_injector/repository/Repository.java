/*
 * Copyright (c) Jakub Zagórski (jaqobb). All rights reserved.
 * Licensed under the MIT license.
 */
package co.jaqobb.dependency_injector.repository;

import java.util.Objects;

public final class Repository {
    public static Repository of(String url) {
        Objects.requireNonNull(url, "url");
        return new Repository(url);
    }

    private final String url;

    private Repository(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Repository that = (Repository) object;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "Repository[" + "url=" + url + "]";
    }
}