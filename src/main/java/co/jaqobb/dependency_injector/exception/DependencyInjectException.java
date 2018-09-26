/*
 * Copyright (c) Jakub Zagórski (jaqobb). All rights reserved.
 * Licensed under the MIT license.
 */
package co.jaqobb.dependency_injector.exception;

public final class DependencyInjectException extends RuntimeException {
    private static final long serialVersionUID = -4632736956520813415L;

    public DependencyInjectException() {
        super();
    }

    public DependencyInjectException(String message) {
        super(message);
    }

    public DependencyInjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyInjectException(Throwable cause) {
        super(cause);
    }
}