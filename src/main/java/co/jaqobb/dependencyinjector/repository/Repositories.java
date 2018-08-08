/*
 * This file is a part of DependencyInjector, licensed under the MIT License (MIT).
 * See the LICENSE.txt file at the root of this project for more details.
 */
package co.jaqobb.dependencyinjector.repository;

// Class that holds some of the more popular repositories.
public final class Repositories {

    // Maven central repository.
    public static final Repository MAVEN_CENTRAL = new Repository("https://repo1.maven.org/maven2/");

    // JCenter repository.
    public static final Repository JCENTER = new Repository("https://jcenter.bintray.com");

    // Suppress default constructor to ensure non-instantiability.
    private Repositories() {
    }

}