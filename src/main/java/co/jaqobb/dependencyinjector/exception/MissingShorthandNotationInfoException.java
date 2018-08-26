package co.jaqobb.dependencyinjector.exception;

public final class MissingShorthandNotationInfoException extends RuntimeException {

    private static final long serialVersionUID = 3080460814499784859L;

    public MissingShorthandNotationInfoException() {
        super();
    }

    public MissingShorthandNotationInfoException(String message) {
        super(message);
    }

    public MissingShorthandNotationInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingShorthandNotationInfoException(Throwable cause) {
        super(cause);
    }

}