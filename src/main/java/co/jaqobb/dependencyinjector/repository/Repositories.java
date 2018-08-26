package co.jaqobb.dependencyinjector.repository;

public final class Repositories {

    public static final Repository MAVEN_CENTRAL = new Repository("https://repo1.maven.org/maven2/");

    public static final Repository JCENTER = new Repository("https://jcenter.bintray.com");

    private Repositories() {
    }

}