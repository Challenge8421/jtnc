/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Carl-Heinz Genzel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */
package de.hsbremen.tc.tnc.tnccs.session.statemachine.exception;

/**
 * Exception signals exceptions that are thrown by the state machine according
 * to its current state.
 *
 * @author Carl-Heinz Genzel
 *
 */
public class StateMachineAccessException extends Exception {

    private static final long serialVersionUID = -269072531330648704L;

    /**
     * Creates an exception with a specified message based on an existing
     * exception.
     *
     * @param message the specified exception message
     * @param cause the existing exception, which has to be encapsulated
     */
    public StateMachineAccessException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an exception with a specified message.
     *
     * @param message the specified exception message
     */
    public StateMachineAccessException(final String message) {
        super(message);
    }

}