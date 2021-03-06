/**
 * The BSD 3-Clause License ("BSD New" or "BSD Simplified")
 *
 * Copyright © 2015 Trust HS Bremen and its Contributors. All rights   
 * reserved.
 *
 * See the CONTRIBUTORS file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.ietf.nea.pa.message;

import de.hsbremen.tc.tnc.message.m.message.ImMessageHeader;

/**
 * IETF RFC 5792 integrity measurement message header.
 *
 *
 */
public class PaMessageHeader implements ImMessageHeader {

    private final short version; // 8 bit(s)
    private final long identifier; // 32 bit(s)
    private long length; // not official part

    /**
     * Creates the header with the given values.
     * @param version the message format version
     * @param identifier the message identifier
     * @param length the message length
     */
    PaMessageHeader(final short version,
            final long identifier, final long length) {
        this.version = version;
        this.identifier = identifier;
        this.length = length;
    }

    @Override
    public short getVersion() {
        return this.version;
    }

    @Override
    public long getIdentifier() {
        return this.identifier;
    }

    @Override
    public long getLength() {
        return length;
    }

    /**
     * Sets the length value of the header.
     * @param length the length of the message
     */
    public void setLength(final long length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "PaMessageHeader [version=" + this.version + ", identifier="
                + this.identifier + ", length=" + this.length + "]";
    }
    
    

}
