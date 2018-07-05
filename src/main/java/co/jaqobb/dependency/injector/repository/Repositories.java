package co.jaqobb.dependency.injector.repository;

/**
 * Collection of the preset repositories.
 */
public final class Repositories {
	/**
	 * Official Maven repository.
	 */
	public static final Repository MAVEN_CENTRAL = new Repository("https://repo1.maven.org/maven2/");
	/**
	 * JCenter repository.
	 */
	public static final Repository JCENTER = new Repository("https://jcenter.bintray.com");
	/**
	 * jaqobb releases repository.
	 */
	public static final Repository JAQOBB_RELEASES = new Repository("https://repo.jaqobb.co/repository/maven-releases/");
	/**
	 * jaqobb snapshots repository.
	 */
	public static final Repository JAQOBB_SNAPSHOTS = new Repository("https://repo.jaqobb.co/repository/maven-snapshots/");

	/**
	 * Private constructor to make sure no one will initialize this class.
	 */
	private Repositories() {
	}
}