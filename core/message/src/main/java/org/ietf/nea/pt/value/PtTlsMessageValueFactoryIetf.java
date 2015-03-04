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
package org.ietf.nea.pt.value;

import java.util.ArrayList;
import java.util.List;

import org.ietf.nea.pt.message.enums.PtTlsMessageTlvFixedLengthEnum;
import org.ietf.nea.pt.value.enums.PtTlsSaslResultEnum;
import org.ietf.nea.pt.value.util.SaslMechanismEntry;

import de.hsbremen.tc.tnc.IETFConstants;
import de.hsbremen.tc.tnc.message.util.ByteBuffer;
import de.hsbremen.tc.tnc.util.NotNull;

/**
 * Factory utility to create a IETF RFC 6876 compliant transport message value.
 *
 * @author Carl-Heinz Genzel
 *
 */
public abstract class PtTlsMessageValueFactoryIetf {

    /**
     * Private constructor should never be invoked.
     */
    private PtTlsMessageValueFactoryIetf() {
        throw new AssertionError();
    }

    /**
     * Creates a version request transport message value.
     * @param minVersion the minimal version supported
     * @param maxVersion the maximal version supported
     * @param preferredVersion the preferred version
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueVersionRequest createVersionRequestValue(
            final short minVersion, final short maxVersion,
            final short preferredVersion) {

        if (minVersion > 0xFF) {
            throw new IllegalArgumentException("Min. version is greater than "
                    + Short.toString((short) 0xFF) + ".");
        }
        if (maxVersion > 0xFF) {
            throw new IllegalArgumentException("Max. version is greater than "
                    + Short.toString((short) 0xFF) + ".");
        }

        if (minVersion > 0xFF) {
            throw new IllegalArgumentException(
                    "Prefered version is greater than "
                            + Short.toString((short) 0xFF) + ".");
        }

        long length = PtTlsMessageTlvFixedLengthEnum.VER_REQ.length();

        return new PtTlsMessageValueVersionRequest(length, minVersion,
                maxVersion, preferredVersion);
    }

    /**
     * Creates a version response transport message value.
     * @param selectedVersion the selected version
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueVersionResponse createVersionResponseValue(
            final short selectedVersion) {

        if (selectedVersion > 0xFF) {
            throw new IllegalArgumentException("Min. version is greater than "
                    + Short.toString((short) 0xFF) + ".");
        }

        long length = PtTlsMessageTlvFixedLengthEnum.VER_RES.length();

        return new PtTlsMessageValueVersionResponse(length, selectedVersion);
    }

    /**
     * Creates a transport message value containing SASL mechanisms.
     * @param mechanisms the supported SASL mechanisms
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueSaslMechanisms createSaslMechanismsValue(
            final SaslMechanismEntry... mechanisms) {

        List<SaslMechanismEntry> mechs = new ArrayList<>();

        long length = 0;

        if (mechanisms != null) {

            for (SaslMechanismEntry saslMechanism : mechanisms) {
                if (saslMechanism.getName().matches("[A-Z0-9\\-_]{1,20}")) {
                    mechs.add(saslMechanism);
                    length += saslMechanism.getNameLength();
                    length += 1; // the names' length field is one byte
                } else {
                    throw new IllegalArgumentException("SASL mechanism name "
                            + saslMechanism.getName()
                            + " does not match the naming requirements.");
                }
            }

        }

        return new PtTlsMessageValueSaslMechanisms(length, mechs);
    }

    /**
     * Creates transport message value with a SASL mechanism selection.
     * @param mechanism the selected SASL mechanism
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueSaslMechanismSelection
        createSaslMechanismSelectionValue(
            final SaslMechanismEntry mechanism) {
        return createSaslMechanismSelectionValue(mechanism, null);
    }

    /**
     * Creates transport message value with a SASL mechanism selection and
     * initial SASL data.
     * @param mechanism the selected SASL mechanism
     * @param initialData the initial SASL data
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueSaslMechanismSelection
        createSaslMechanismSelectionValue(
            final SaslMechanismEntry mechanism, final byte[] initialData) {

        NotNull.check("Mechanism cannot be null.", mechanism);

        long length = 0;

        if (mechanism.getName().matches("[A-Z0-9\\-_]{1,20}")) {
            length += mechanism.getNameLength();
            length += PtTlsMessageTlvFixedLengthEnum.SASL_SEL.length();
        } else {
            throw new IllegalArgumentException("SASL mechanism name "
                    + mechanism.getName()
                    + " does not match the naming requirements.");
        }

        if (initialData != null) {
            length += initialData.length;
            return new PtTlsMessageValueSaslMechanismSelection(length,
                    mechanism, initialData);
        } else {
            return new PtTlsMessageValueSaslMechanismSelection(length,
                    mechanism);
        }

    }

    /**
     * Creates transport message value with a SASL authentication data.
     * @param authenticationData the SASL authentication data
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueSaslAuthenticationData
        createSaslAuthenticationDataValue(
            final byte[] authenticationData) {

        NotNull.check("Data cannot be null.", authenticationData);

        return new PtTlsMessageValueSaslAuthenticationData(
                authenticationData.length, authenticationData);
    }

    /**
     * Creates transport message value with a SASL authentication result.
     * @param result the SASL authentication result
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueSaslResult createSaslResultValue(
            final PtTlsSaslResultEnum result) {
        return createSaslResultValue(result, null);
    }

    /**
     * Creates transport message value with a SASL authentication result and
     * additional SASL result data.
     * @param result the SASL authentication result
     * @param resultData the additional result data
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueSaslResult createSaslResultValue(
            final PtTlsSaslResultEnum result, final byte[] resultData) {

        NotNull.check("Result cannot be null.", result);

        long length = PtTlsMessageTlvFixedLengthEnum.SASL_RLT.length();

        if (resultData != null) {
            length += resultData.length;
            return new PtTlsMessageValueSaslResult(length, result, resultData);
        } else {
            return new PtTlsMessageValueSaslResult(length, result);
        }
    }

    /**
     * Creates transport message error value with a byte copy of the
     * erroneous message.
     * @param errorVendorId the error vendor ID
     * @param errorCode the error code describing the error
     * @param messageCopy the byte copy of the erroneous message
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueError createErrorValue(
            final long errorVendorId, final long errorCode,
            final byte[] messageCopy) {

        NotNull.check("Message copy cannot be null.", messageCopy);

        if (errorVendorId > IETFConstants.IETF_MAX_VENDOR_ID) {
            throw new IllegalArgumentException("Vendor ID is greater than "
                    + Long.toString(IETFConstants.IETF_MAX_VENDOR_ID) + ".");
        }
        if (errorCode > IETFConstants.IETF_MAX_TYPE) {
            throw new IllegalArgumentException("Type is greater than "
                    + Long.toString(IETFConstants.IETF_MAX_TYPE) + ".");
        }

        long length = PtTlsMessageTlvFixedLengthEnum.ERR_VALUE.length()
                + messageCopy.length;

        return new PtTlsMessageValueError(errorVendorId, errorCode, length,
                messageCopy);
    }

    /**
     * Creates transport message value with a TNCCS batch.
     * @param tnccsData the serialized TNCCS batch
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValuePbBatch createPbBatchValue(
            final ByteBuffer tnccsData) {

        NotNull.check("Data cannot be null.", tnccsData);

        return new PtTlsMessageValuePbBatch(tnccsData.bytesWritten(),
                tnccsData);
    }

    /**
     * Creates an experimental transport message value with the
     * given content.
     * @param value the value content
     * @return an IETF RFC 6876 compliant transport message value
     */
    public static PtTlsMessageValueExperimental createExperimentalValue(
            final String value) {

        String m = (value != null) ? value : "";

        return new PtTlsMessageValueExperimental(m.length(), m);
    }
}
