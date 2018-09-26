/*
 * Copyright (c) Jakub Zagórski (jaqobb). All rights reserved.
 * Licensed under the MIT license.
 */
package co.jaqobb.dependency_injector.exception;

public final class DependencyDownloadException extends RuntimeException {
    private static final long serialVersionUID = 8226132361048630899L;

    public DependencyDownloadException() {
        super();
    }

    public DependencyDownloadException(String message) {
        super(message);
    }

    public DependencyDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public DependencyDownloadException(Throwable cause) {
        super(cause);
    }
}