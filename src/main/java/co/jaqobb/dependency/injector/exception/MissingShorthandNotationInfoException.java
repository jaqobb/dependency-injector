package co.jaqobb.dependency.injector.exception;

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
   */
  public MissingShorthandNotationInfoException(String message) {
    super(message);
  }

  /**
   * Constructs new missing shorthand notation info exception with the given message and cause.
   */
  public MissingShorthandNotationInfoException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs new missing shorthand notation info exception with no message and the given cause.
   */
  public MissingShorthandNotationInfoException(Throwable cause) {
    super(cause);
  }

}