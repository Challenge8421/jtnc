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
package org.ietf.nea.pa.attribute.util;

import org.ietf.nea.pa.attribute.enums.PaAttributeTlvFixedLengthEnum;

import de.hsbremen.tc.tnc.IETFConstants;

/**
 * Builder to build an integrity measurement unsupported version error
 * information value compliant to RFC 5792. It can be used in a fluent way.
 *
 *
 */
public class PaAttributeValueErrorInformationUnsupportedVersionBuilderIetf
        implements PaAttributeValueErrorInformationUnsupportedVersionBuilder {

    private static final short PREFERRED_VERSION =
            IETFConstants.IETF_RFC5792_VERSION_NUMBER;

    private long length;
    private MessageHeaderDump messageHeader;
    private short maxVersion;
    private short minVersion;

    /**
     * Creates the builder using default values.
     * <ul>
     * <li>Length: Fixed value length only</li>
     * <li>Message header: Dummy header</li>
     * <li>Maximum version: RFC 5792 version</li>
     * <li>Minimum version: RFC 5792 version</li>
     * </ul>
     */
    public PaAttributeValueErrorInformationUnsupportedVersionBuilderIetf() {
        final int fixedMinMaxLength = 4;
        this.length = PaAttributeTlvFixedLengthEnum.ERR_INF.length()
                + PaAttributeTlvFixedLengthEnum.MESSAGE.length()
                + fixedMinMaxLength;
        this.messageHeader = new MessageHeaderDump((short) 0, new byte[0], 0L);
        this.minVersion = PREFERRED_VERSION;
        this.maxVersion = PREFERRED_VERSION;
    }

    @Override
    public PaAttributeValueErrorInformationUnsupportedVersionBuilder
        setMessageHeader(final MessageHeaderDump messageHeader) {
        if (messageHeader != null) {
            this.messageHeader = messageHeader;
        }
        return this;
    }

    @Override
    public PaAttributeValueErrorInformationUnsupportedVersionBuilder
        setMaxVersion(final short maxVersion) {
        this.maxVersion = maxVersion;
        return this;
    }

    @Override
    public PaAttributeValueErrorInformationUnsupportedVersionBuilder
        setMinVersion(final short minVersion) {
        this.minVersion = minVersion;
        return this;
    }

    @Override
    public PaAttributeValueErrorInformationUnsupportedVersion toObject() {
        return new PaAttributeValueErrorInformationUnsupportedVersion(
                this.length, this.messageHeader, this.maxVersion,
                this.minVersion);
    }

    @Override
    public PaAttributeValueErrorInformationUnsupportedVersionBuilder newInstance() {
        return new PaAttributeValueErrorInformationUnsupportedVersionBuilderIetf();
    }

}
