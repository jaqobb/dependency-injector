/*
 * This file is a part of dependency-injector, licensed under the MIT License.
 *
 * Copyright (c) jaqobb (Jakub Zagórski) <jaqobb@jaqobb.co>
 * Copyright (c) contributors
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
 * Exception that is being thrown when the used shorthand notation is missing group id, artifact id or version.
 */
public final class MissingShorthandNotationInfoException extends RuntimeException
{
	/**
	 * Serial version unique id of the exception.
	 */
	private static final long serialVersionUID = -7204229188454436551L;

	/**
	 * Constructs new missing shorthand notation info exception with no message and no cause.
	 */
	public MissingShorthandNotationInfoException()
	{
		super();
	}

	/**
	 * Constructs new missing shorthand notation info exception with the given message and no cause.
	 *
	 * @param message A message of the expection.
	 */
	public MissingShorthandNotationInfoException(String message)
	{
		super(message);
	}

	/**
	 * Constructs new missing shorthand notation info exception with the given message and cause.
	 *
	 * @param meessage A message of the exception.
	 * @param cause A cause of the exception.
	 */
	public MissingShorthandNotationInfoException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructs new missing shorthand notation info exception with no message and the given cause.
	 *
	 * @param cause A cause of the exception.
	 */
	public MissingShorthandNotationInfoException(Throwable cause)
	{
		super(cause);
	}
}