/**
 * The BSD 3-Clause License ("BSD New" or "BSD Simplified")
 *
 * Copyright (c) 2015, Carl-Heinz Genzel
 * All rights reserved.
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
package org.ietf.nea.pb.message.util;

import java.nio.charset.Charset;

import org.ietf.nea.pb.message.enums.PbMessageTlvFixedLengthEnum;
import org.ietf.nea.pb.validate.rules.LangCodeStringLimit;
import org.ietf.nea.pb.validate.rules.NoNullTerminatedString;
import org.ietf.nea.pb.validate.rules.NoZeroString;

import de.hsbremen.tc.tnc.message.exception.RuleException;

/**
 * Builder to build a TNCCS message string remediation parameter value
 * compliant to RFC 5793. It evaluates the given values and can be used in a
 * fluent way.
 *
 *
 */
public class PbMessageValueRemediationParameterStringBuilderIetf implements
        PbMessageValueRemediationParameterStringBuilder {

    private long length;
    private String remediationString; // variable length, UTF-8 encoded, NUL
                                      // termination MUST NOT be included.
    private String langCode; // variable length, US-ASCII string composed of a
                             // well-formed RFC 4646 [3] language tag

    /**
     * Creates the builder using default values.
     * <ul>
     * <li>Length: Fixed value length only</li>
     * <li>String: ""</li>
     * <li>Language: ""</li>
     * </ul>
     */
    public PbMessageValueRemediationParameterStringBuilderIetf() {
        this.length = PbMessageTlvFixedLengthEnum.REM_STR_SUB_VALUE.length();
        this.remediationString = "";
        this.langCode = "";
    }

    @Override
    public PbMessageValueRemediationParameterStringBuilder setRemediationString(
            final String remediationString) throws RuleException {

        NoZeroString.check(remediationString);
        NoNullTerminatedString.check(remediationString);
        this.remediationString = remediationString;
        this.updateLength();

        return this;
    }

    @Override
    public PbMessageValueRemediationParameterStringBuilder setLangCode(
            final String langCode) throws RuleException {

        // Zero length string for language code allowed.
        if (langCode != null) {
            NoNullTerminatedString.check(langCode);
            LangCodeStringLimit.check(langCode);
            this.langCode = langCode;
            this.updateLength();
        }

        return this;
    }

    @Override
    public PbMessageValueRemediationParameterString toObject()
            throws RuleException {

        // check again because it has to set properly
        NoZeroString.check(this.remediationString);

        return new PbMessageValueRemediationParameterString(this.length,
                this.remediationString, this.langCode);
    }

    @Override
    public PbMessageValueRemediationParameterStringBuilder newInstance() {

        return new PbMessageValueRemediationParameterStringBuilderIetf();
    }

    /**
     * Updates the length according to the values set.
     */
    private void updateLength() {
        this.length = PbMessageTlvFixedLengthEnum.REM_STR_SUB_VALUE.length();
        if (remediationString.length() > 0) {
            this.length += remediationString.getBytes(
                    Charset.forName("UTF-8")).length;
        }
        if (langCode.length() > 0) {
            this.length += langCode.getBytes(
                    Charset.forName("US-ASCII")).length;
        }
    }
}
