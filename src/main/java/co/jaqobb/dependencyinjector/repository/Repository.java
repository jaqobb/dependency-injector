package co.jaqobb.dependencyinjector.repository;

import java.util.Objects;

public final class Repository
{
    private final String url;

    public Repository(String url)
    {
        this.url = Objects.requireNonNull(url, "url");
    }

    public String getUrl()
    {
        return this.url;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null || this.getClass() != object.getClass())
        {
            return false;
        }
        Repository that = (Repository) object;
        return Objects.equals(this.url, that.url);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.url);
    }

    @Override
    public String toString()
    {
        return "Repository{" + "url=" + this.url + "}";
    }
}