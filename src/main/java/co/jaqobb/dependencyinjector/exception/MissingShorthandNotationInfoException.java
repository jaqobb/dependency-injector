/*
 * This file is a part of DependencyInjector, licensed under the MIT License (MIT).
 * See the LICENSE.txt file at the root of this project for more details.
 */
package co.jaqobb.dependencyinjector.exception;

// Exception that is being thrown when the used shorthand notation is missing group id, artifact id, or version.
public final class MissingShorthandNotationInfoException extends RuntimeException {

    // The unique id of this exception.
    private static final long serialVersionUID = -7204229188454436551L;

    // Constructs a new {@code MissingShorthandNotationInfoException} object with no message and cause.
    public MissingShorthandNotationInfoException() {
        super();
    }

    /**
     * Constructs a new {@code MissingShorthandNotationInfoException} object with the given message and no cause.
     *
     * @param message The message of the exception
     */
    public MissingShorthandNotationInfoException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code MissingShorthandNotationInfoException} object with the given message and cause.
     *
     * @param message The message of the exception
     * @param cause The cause of the exception
     */
    public MissingShorthandNotationInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code MissingShorthandNotationInfoException} object with no message and the given cause.
     *
     * @param cause A cause of the exception
     */
    public MissingShorthandNotationInfoException(Throwable cause) {
        super(cause);
    }
}