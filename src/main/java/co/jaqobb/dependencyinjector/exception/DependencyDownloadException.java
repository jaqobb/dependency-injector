package co.jaqobb.dependencyinjector.exception;

public final class DependencyDownloadException extends RuntimeException
{
    private static final long serialVersionUID = 945863300106644187L;

    public DependencyDownloadException()
    {
        super();
    }

    public DependencyDownloadException(String message)
    {
        super(message);
    }

    public DependencyDownloadException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public DependencyDownloadException(Throwable cause)
    {
        super(cause);
    }
}