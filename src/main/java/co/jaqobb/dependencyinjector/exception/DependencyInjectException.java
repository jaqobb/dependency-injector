package co.jaqobb.dependencyinjector.exception;

public final class DependencyInjectException extends RuntimeException {

    private static final long serialVersionUID = -2081996100043539212L;

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