/*
 * MIT License
 *
 * Copyright (c) 2018 Jakub Zagórski
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package co.jaqobb.dependency.injector.repository;

import java.util.Objects;

/**
 * Class that holds repository url.
 */
public final class Repository
{
	/**
	 * Url of the {@code Repository}.
	 */
	private final String url;

	/**
	 * Constructs new {@code Repository}
	 * instance with the given {@code url}.
	 *
	 * @param url a url.
	 *
	 * @throws NullPointerException if the given {@code url} is {@code null}.
	 */
	public Repository(String url)
	{
		this.url = Objects.requireNonNull(url, "url");
	}

	/**
	 * Returns a url of this {@code Repository}.
	 *
	 * @return a url of this {@code Repository}.
	 */
	public String getUrl()
	{
		return this.url;
	}

	/**
	 * Checks if the given {@code object}
	 * is the same as this class.
	 *
	 * @param object an object to be checked.
	 *
	 * @return {@code true} if both objects
	 * are the same, {@code false} otherwise.
	 */
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

	/**
	 * Returns a hash code of this class.
	 *
	 * @return a hash code of this class.
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(this.url);
	}

	/**
	 * Returns a nice looking representation of this class.
	 *
	 * @return a nice looking representation of this class.
	 */
	@Override
	public String toString()
	{
		return "Repository{" + "url=" + this.url + "}";
	}
}