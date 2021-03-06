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
package org.ietf.nea.pa.attribute;

import java.nio.charset.Charset;

import org.ietf.nea.pa.attribute.enums.PaAttributeTlvFixedLengthEnum;
import org.ietf.nea.pa.validate.rules.NoNullTerminatedString;
import org.ietf.nea.pa.validate.rules.StringLengthLimit;

import de.hsbremen.tc.tnc.message.exception.RuleException;

/**
 * Builder to build an integrity measurement string version attribute value
 * compliant to RFC 5792. It evaluates the given values and can be used in a
 * fluent way.
 *
 *
 */
public class PaAttributeValueStringVersionBuilderIetf implements
        PaAttributeValueStringVersionBuilder {

    private static final int MAX_STRING_LENGTH = 0xFF;

    private long length;
    private String productVersion;
    private String buildVersion;
    private String configVersion;

    /**
     * Creates the builder using default values.
     * <ul>
     * <li>Length: Fixed value length only</li>
     * <li>Product version: ""</li>
     * <li>Build version: ""</li>
     * <li>Configuration version: ""</li>
     * </ul>
     */
    public PaAttributeValueStringVersionBuilderIetf() {
        this.length = PaAttributeTlvFixedLengthEnum.STR_VER.length();
        this.productVersion = "";
        this.buildVersion = "";
        this.configVersion = "";
    }

    @Override
    public PaAttributeValueStringVersionBuilder setProductVersion(
            final String productVersion) throws RuleException {
        if (productVersion != null) {
            NoNullTerminatedString.check(productVersion);
            StringLengthLimit.check(productVersion, MAX_STRING_LENGTH);
            this.productVersion = productVersion;
            this.updateLength();
        }
        return this;
    }

    @Override
    public PaAttributeValueStringVersionBuilder setBuildNumber(
            final String buildNumber) throws RuleException {
        if (buildNumber != null) {
            NoNullTerminatedString.check(buildNumber);
            StringLengthLimit.check(buildNumber, MAX_STRING_LENGTH);
            this.buildVersion = buildNumber;
            this.updateLength();
        }
        return this;
    }

    @Override
    public PaAttributeValueStringVersionBuilder setConfigurationVersion(
            final String configVersion) throws RuleException {
        if (configVersion != null) {
            NoNullTerminatedString.check(configVersion);
            StringLengthLimit.check(configVersion, MAX_STRING_LENGTH);
            this.configVersion = configVersion;
            this.updateLength();
        }
        return this;
    }

    @Override
    public PaAttributeValueStringVersion toObject() {
        return new PaAttributeValueStringVersion(this.length,
                this.productVersion, this.buildVersion, this.configVersion);
    }

    @Override
    public PaAttributeValueStringVersionBuilder newInstance() {
        return new PaAttributeValueStringVersionBuilderIetf();
    }

    /**
     * Updates the length according to the given version information.
     */
    private void updateLength() {
        this.length = PaAttributeTlvFixedLengthEnum.STR_VER.length();
        if (productVersion.length() > 0) {
            this.length += productVersion.getBytes(
                    Charset.forName("UTF-8")).length;
        }

        if (buildVersion.length() > 0) {
            this.length += buildVersion.getBytes(
                    Charset.forName("UTF-8")).length;
        }

        if (configVersion.length() > 0) {
            this.length += configVersion.getBytes(
                    Charset.forName("UTF-8")).length;
        }
    }

}
