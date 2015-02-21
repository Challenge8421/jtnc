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
package de.hsbremen.tc.tnc.tnccs.adapter.tnccs;

import java.util.HashSet;
import java.util.Set;

import org.trustedcomputinggroup.tnc.ifimv.IMV;
import org.trustedcomputinggroup.tnc.ifimv.TNCS;
import org.trustedcomputinggroup.tnc.ifimv.TNCException;

import de.hsbremen.tc.tnc.attribute.Attributed;
import de.hsbremen.tc.tnc.attribute.DefaultTncAttributeTypeFactory;
import de.hsbremen.tc.tnc.exception.TncException;
import de.hsbremen.tc.tnc.report.SupportedMessageType;
import de.hsbremen.tc.tnc.report.SupportedMessageTypeFactory;
import de.hsbremen.tc.tnc.report.enums.ImHandshakeRetryReasonEnum;
import de.hsbremen.tc.tnc.tnccs.im.GlobalHandshakeRetryListener;
import de.hsbremen.tc.tnc.tnccs.im.manager.ImManager;

/**
 * TNCS adapter according to IETF/TCG specifications.
 * Implementing a simple IF-IMV TNCS interface.
 *
 * @author Carl-Heinz Genzel
 *
 */
class TncsAdapterIetf implements TNCS {

    private final ImManager<IMV> moduleManager;
    private final GlobalHandshakeRetryListener listener;
    private final Attributed attributes;

    /**
     * Creates a TNCS adapter with the specified arguments.
     *
     * @param moduleManager the IMV manager
     * @param attributes the accessible TNCC attributes
     * @param listener the global handshake retry listener
     */
    TncsAdapterIetf(final ImManager<IMV> moduleManager,
            final Attributed attributes,
            final GlobalHandshakeRetryListener listener) {
        this.moduleManager = moduleManager;
        this.attributes = attributes;
        this.listener = listener;
    }

    /**
     * Returns the IMV manager. Especially important for inheritance.
     *
     * @return the IMV manager
     */
    protected ImManager<IMV> getManager() {
        return this.moduleManager;
    }

    @Override
    public void reportMessageTypes(final IMV imv, final long[] supportedTypes)
            throws TNCException {

        Set<SupportedMessageType> sTypes = new HashSet<>();

        if (supportedTypes != null) {
            for (long l : supportedTypes) {
                try {
                    SupportedMessageType mType = SupportedMessageTypeFactory
                            .createSupportedMessageTypeLegacy(l);
                    sTypes.add(mType);
                } catch (IllegalArgumentException e) {
                    throw new TNCException(e.getMessage(),
                            TNCException.TNC_RESULT_INVALID_PARAMETER);
                }
            }
        }

        try {
            this.moduleManager.reportSupportedMessagesTypes(imv, sTypes);
        } catch (TncException e) {
            throw new TNCException(e.getMessage(), e.getResultCode().id());
        }
    }

    @Override
    public void requestHandshakeRetry(final IMV imv, final long reason)
            throws TNCException {
        // TODO is the IMC needed as parameter?
        try {
            this.listener
                    .requestGlobalHandshakeRetry(ImHandshakeRetryReasonEnum
                            .fromId(reason));
        } catch (TncException e) {
            throw new TNCException(e.getMessage(), e.getResultCode().id());
        }

    }

    @Override
    public Object getAttribute(final long attributeID) throws TNCException {
        try {
            return this.attributes.getAttribute(DefaultTncAttributeTypeFactory
                    .getInstance().fromId(attributeID));
        } catch (TncException e) {
            throw new TNCException(e.getMessage(), e.getResultCode().id());
        }
    }

    @Override
    public void setAttribute(final long attributeID,
            final Object attributeValue) throws TNCException {
        try {
            this.attributes.setAttribute(DefaultTncAttributeTypeFactory
                    .getInstance().fromId(attributeID), attributeValue);
        } catch (TncException e) {
            throw new TNCException(e.getMessage(), e.getResultCode().id());
        }
    }

}