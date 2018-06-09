package co.jaqobb.dependency.injector.exception;

/**
 * Exception that is being thrown when the used shorthand notation is missing group id, artifact id or version.
 */
public final class ShorthandNotationException extends RuntimeException {

  /**
   * Serial version unique id of the exception.
   */
  private static final long serialVersionUID = -7204229188454436551L;

  /**
   * Constructs new shorthand notation  exception with no message and no cause.
   */
  public ShorthandNotationException() {
    super();
  }

  /**
   * Constructs new shorthand notation exception with the given message and no cause.
   */
  public ShorthandNotationException(String message) {
    super(message);
  }

  /**
   * Constructs new shorthand notation exception with the given message and cause.
   */
  public ShorthandNotationException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs new shorthand notation exception with no message and the given cause.
   */
  public ShorthandNotationException(Throwable cause) {
    super(cause);
  }

}