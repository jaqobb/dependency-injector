package co.jaqobb.dependencyinjector.exception;

/**
 * Exception that is being thrown when the dependency failed to inject.
 */
public final class DependencyInjectException extends RuntimeException {
	/**
	 * Serial version unique id of the exception.
	 */
	private static final long serialVersionUID = 6564083847102002873L;

	/**
	 * Constructs new dependency inject exception with no message and no cause.
	 */
	public DependencyInjectException() {
		super();
	}

	/**
	 * Constructs new dependency inject exception with the given message and no cause.
	 *
	 * @param message A message of the expection.
	 */
	public DependencyInjectException(String message) {
		super(message);
	}

	/**
	 * Constructs new dependency inject exception with the given message and cause.
	 *
	 * @param meessage A message of the exception.
	 * @param cause A cause of the exception.
	 */
	public DependencyInjectException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs new dependency inject exception with no message and the given cause.
	 *
	 * @param cause A cause of the exception.
	 */
	public DependencyInjectException(Throwable cause) {
		super(cause);
	}
}