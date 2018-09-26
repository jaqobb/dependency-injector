/*
 * Copyright (c) Jakub Zagórski (jaqobb). All rights reserved.
 * Licensed under the MIT license.
 */
package co.jaqobb.dependency_injector.exception;

public final class MissingShorthandNotationInfoException extends RuntimeException {
    private static final long serialVersionUID = -3384490868082784516L;

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