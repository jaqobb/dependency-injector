/*
 * The MIT License.
 *
 * Copyright (c) jaqobb (Jakub Zagórski) <jaqobb@jaqobb.co>
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

package co.jaqobb.dependency.injector.exception;

/**
 * Exception that is being thrown when the dependency failed to download.
 */
public final class DependencyDownloadException extends RuntimeException
{
	/**
	 * Serial version unique id of the exception.
	 */
	private static final long serialVersionUID = 6564083847102002873L;

	/**
	 * Constructs new dependency download exception with no message and no cause.
	 */
	public DependencyDownloadException()
	{
		super();
	}

	/**
	 * Constructs new dependency download exception with the given message and no cause.
	 */
	public DependencyDownloadException(String message)
	{
		super(message);
	}

	/**
	 * Constructs new dependency download exception with the given message and cause.
	 */
	public DependencyDownloadException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructs new dependency download exception with no message and the given cause.
	 */
	public DependencyDownloadException(Throwable cause)
	{
		super(cause);
	}
}