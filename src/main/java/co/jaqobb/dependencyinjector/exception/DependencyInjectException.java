/*
 * This file is a part of DependencyInjector, licensed under the MIT License (MIT).
 * See the LICENSE.txt file at the root of this project for more details.
 */
package co.jaqobb.dependencyinjector.exception;

// Exception that is being thrown when the dependency failed to inject.
public final class DependencyInjectException extends RuntimeException {

    // The unique id of this exception.
    private static final long serialVersionUID = 6564083847102002873L;

    // Constructs a new {@code DependencyInjectException} object with no message and cause.
    public DependencyInjectException() {
        super();
    }

    /**
     * Constructs a new {@code DependencyInjectException} object with the given message and no cause.
     *
     * @param message The message of the exception
     */
    public DependencyInjectException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DependencyInjectException} object with the given message and cause.
     *
     * @param message The message of the exception
     * @param cause The cause of the exception
     */
    public DependencyInjectException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code DependencyInjectException} object with no message and the given cause.
     *
     * @param cause A cause of the exception
     */
    public DependencyInjectException(Throwable cause) {
        super(cause);
    }

}