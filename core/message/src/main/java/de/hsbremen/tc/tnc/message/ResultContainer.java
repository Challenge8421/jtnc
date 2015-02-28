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
package de.hsbremen.tc.tnc.message;

import java.util.List;

import de.hsbremen.tc.tnc.message.exception.ValidationException;

/**
 * Generic base for an helper object to pass on a transmission result such as a
 * message or batch and minor validation errors after parsing for further
 * processing.
 *
 * @author Carl-Heinz Genzel
 *
 * @param <T> the type of the transmission object, the type must extend the
 * data marker.
 *
 */
public interface ResultContainer<T extends Data> {

    /**
     * Returns a list of minor errors that occurred during parsing.
     *
     * @return a list of minor validation errors
     */
    List<ValidationException> getExceptions();

    /**
     * Returns the transmission result such as a message or batch.
     *
     * @return the parsed transmission result
     */
    T getResult();
}
