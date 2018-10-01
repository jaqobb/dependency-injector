/*
 * Copyright (c) Jakub Zagórski (jaqobb). All rights reserved.
 * Licensed under the MIT license.
 */
package co.jaqobb.dependency_injector.repository;

public final class Repositories {
    public static final Repository JCENTER       = Repository.of("https://jcenter.bintray.com");
    public static final Repository MAVEN_CENTRAL = Repository.of("https://repo1.maven.org/maven2/");

    private Repositories() {
    }
}