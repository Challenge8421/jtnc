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
package de.hsbremen.tc.tnc.im.adapter.connection;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.trustedcomputinggroup.tnc.ifimc.IMCConnection;

import de.hsbremen.tc.tnc.attribute.DefaultTncAttributeTypeFactory;
import de.hsbremen.tc.tnc.attribute.TncAttributeType;
import de.hsbremen.tc.tnc.exception.TncException;
import de.hsbremen.tc.tnc.message.m.message.ImMessage;
import de.hsbremen.tc.tnc.message.m.serialize.bytebuffer.ImWriter;
import de.hsbremen.tc.tnc.util.NotNull;

/**
 * Adapter factory for an IMC connection according to IETF/TCG specifications.
 *
 * @author Carl-Heinz Genzel
 */
public class ImcConnectionAdapterFactoryIetf implements
        ImcConnectionAdapterFactory {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ImcConnectionAdapterFactory.class);
    private final ImWriter<ImMessage> writer;

    /**
     * Creates a connection factory with the specified message writer.
     * @param writer the message writer
     */
    @SuppressWarnings("unchecked")
    public ImcConnectionAdapterFactoryIetf(
            final ImWriter<? extends ImMessage> writer) {
        NotNull.check("Writer cannot be null.", writer);

        this.writer = (ImWriter<ImMessage>) writer;
    }

    @Override
    public ImcConnectionAdapter createConnectionAdapter(
            final IMCConnection connection) {

        NotNull.check("Connection cannot be null.", connection);

        ImcConnectionAdapter adapter =
                new ImcConnectionAdapterIetf(writer, connection);

        if (LOGGER.isDebugEnabled()) {
            this.writeConnectionInformationToDebugLog(adapter);
        }

        return adapter;
    }

    /**
     * Formats the available connection information and writes it to a log file
     * for debugging.
     *
     * @param connection the connection to examine
     */
    private void writeConnectionInformationToDebugLog(
            final ImcConnectionAdapter connection) {
        StringBuilder b = new StringBuilder();
        b.append("Create session with connection ")
                .append(connection.toString()).append(".\n");

        b.append("The following parameters are set and accessible:\n");
        List<TncAttributeType> clientTypes = DefaultTncAttributeTypeFactory
                .getInstance().getClientTypes();
        try {
            for (TncAttributeType tncAttributeType : clientTypes) {

                try {
                    Object o = connection.getAttribute(tncAttributeType);
                    if (o != null) {
                        b.append(tncAttributeType.toString() + ": ");
                        b.append(o.toString()).append("\n");
                    }
                } catch (TncException e) {
                    b.append("Not accessible. Reason: ")
                            .append(e.getResultCode().toString()).append("\n");
                }
            }
        } catch (UnsupportedOperationException e) {
            b.append("Connection does not support parameter access.");
        }

        LOGGER.debug(b.toString());
    }

}