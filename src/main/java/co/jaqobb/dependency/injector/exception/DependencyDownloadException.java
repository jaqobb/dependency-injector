package co.jaqobb.dependency.injector.exception;

/**
 * Exception that is being thrown when the dependency failed to download.
 */
public final class DependencyDownloadException extends RuntimeException {
	/**
	 * Serial version unique id of the exception.
	 */
	private static final long serialVersionUID = 6564083847102002873L;

	/**
	 * Constructs new dependency download exception with no message and no cause.
	 */
	public DependencyDownloadException() {
		super();
	}

	/**
	 * Constructs new dependency download exception with the given message and no cause.
	 *
	 * @param message A message of the expection.
	 */
	public DependencyDownloadException(String message) {
		super(message);
	}

	/**
	 * Constructs new dependency download exception with the given message and cause.
	 *
	 * @param meessage A message of the exception.
	 * @param cause A cause of the exception.
	 */
	public DependencyDownloadException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs new dependency download exception with no message and the given cause.
	 *
	 * @param cause A cause of the exception.
	 */
	public DependencyDownloadException(Throwable cause) {
		super(cause);
	}
}