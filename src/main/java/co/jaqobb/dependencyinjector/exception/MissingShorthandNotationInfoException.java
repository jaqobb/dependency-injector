package co.jaqobb.dependencyinjector.exception;

/**
 * Exception that is being thrown when the used shorthand notation is missing group id, artifact id or version.
 */
public final class MissingShorthandNotationInfoException extends RuntimeException {
	/**
	 * Serial version unique id of the exception.
	 */
	private static final long serialVersionUID = -7204229188454436551L;

	/**
	 * Constructs new missing shorthand notation info exception with no message and no cause.
	 */
	public MissingShorthandNotationInfoException() {
		super();
	}

	/**
	 * Constructs new missing shorthand notation info exception with the given message and no cause.
	 *
	 * @param message A message of the expection.
	 */
	public MissingShorthandNotationInfoException(String message) {
		super(message);
	}

	/**
	 * Constructs new missing shorthand notation info exception with the given message and cause.
	 *
	 * @param meessage A message of the exception.
	 * @param cause A cause of the exception.
	 */
	public MissingShorthandNotationInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs new missing shorthand notation info exception with no message and the given cause.
	 *
	 * @param cause A cause of the exception.
	 */
	public MissingShorthandNotationInfoException(Throwable cause) {
		super(cause);
	}
}