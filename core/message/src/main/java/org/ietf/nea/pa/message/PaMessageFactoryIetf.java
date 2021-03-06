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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.ietf.nea.pa.attribute.PaAttribute;
import org.ietf.nea.pa.attribute.enums.PaAttributeTlvFixedLengthEnum;

import de.hsbremen.tc.tnc.message.exception.RuleException;
import de.hsbremen.tc.tnc.message.exception.ValidationException;
import de.hsbremen.tc.tnc.message.m.attribute.ImAttribute;

/**
 * Factory utility to create an IETF RFC 5792 compliant integrity measurement
 * component message.
 *
 *
 */
public final class PaMessageFactoryIetf {

    /**
     * Private constructor should never be invoked.
     */
    private PaMessageFactoryIetf() {
        throw new AssertionError();
    }

    /**
     * Creates a message with the given values.
     *
     * @param identifier the connection unique message identifier
     * @param attributes the contained integrity measurement attributes
     * @return an IETF RFC 5792 compliant integrity measurement component
     * message
     * @throws ValidationException if creation fails because of invalid values
     */
    public static PaMessage createMessage(final long identifier,
            final List<? extends ImAttribute> attributes)
                    throws ValidationException {

        List<? extends ImAttribute> attrs = (attributes != null) ? attributes
                : new ArrayList<ImAttribute>(0);

        PaMessageHeaderBuilderIetf builder = new PaMessageHeaderBuilderIetf();

        List<PaAttribute> filteredAttributes = new LinkedList<>();
        try {
            builder.setIdentifier(identifier);

            long l = 0;
            for (ImAttribute attr : attrs) {
                if (attr instanceof PaAttribute) {
                    PaAttribute a = (PaAttribute) attr;
                    l += a.getHeader().getLength();
                    filteredAttributes.add(a);
                } else {
                    throw new IllegalArgumentException("Attribute type "
                            + attr.getClass().getCanonicalName()
                            + " not supported. Attribute must be of type "
                            + PaMessage.class.getCanonicalName() + ".");
                }

            }

            builder.setLength(l
                    + PaAttributeTlvFixedLengthEnum.MESSAGE.length());

        } catch (RuleException e) {
            throw new ValidationException(e.getMessage(), e,
                    ValidationException.OFFSET_NOT_SET);
        }

        PaMessage msg = new PaMessage(builder.toObject(), filteredAttributes);

        return msg;
    }

}
