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

import org.ietf.nea.pa.attribute.enums.PaAttributeForwardingStatusEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeTlvFixedLengthEnum;
import org.ietf.nea.pa.validate.rules.ForwardingStatus;

import de.hsbremen.tc.tnc.message.exception.RuleException;

/**
 * Builder to build an integrity measurement traffic forwarding status attribute
 * value compliant to RFC 5792. It evaluates the given values and can be used in
 * a fluent way.
 *
 *
 */
public class PaAttributeValueForwardingEnabledBuilderIetf implements
        PaAttributeValueForwardingEnabledBuilder {

    private long length;
    private PaAttributeForwardingStatusEnum status;

    /**
     * Creates the builder using default values.
     * <ul>
     * <li>Length: Fixed value length only</li>
     * <li>Status: Unknown</li>
     * </ul>
     */
    public PaAttributeValueForwardingEnabledBuilderIetf() {
        this.length = PaAttributeTlvFixedLengthEnum.FWD_EN.length();
        this.status = PaAttributeForwardingStatusEnum.UNKNWON;
    }

    @Override
    public PaAttributeValueForwardingEnabledBuilder setStatus(final long status)
            throws RuleException {

        ForwardingStatus.check(status);
        this.status = PaAttributeForwardingStatusEnum.fromId(status);

        return this;
    }

    @Override
    public PaAttributeValueForwardingEnabled toObject() {

        return new PaAttributeValueForwardingEnabled(this.length, this.status);
    }

    @Override
    public PaAttributeValueForwardingEnabledBuilder newInstance() {
        // TODO Auto-generated method stub
        return new PaAttributeValueForwardingEnabledBuilderIetf();
    }

}
