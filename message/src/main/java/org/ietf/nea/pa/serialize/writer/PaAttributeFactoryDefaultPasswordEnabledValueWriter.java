package org.ietf.nea.pa.serialize.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.ietf.nea.pa.attribute.PaAttributeValueFactoryDefaultPasswordEnabled;

import de.hsbremen.tc.tnc.message.exception.SerializationException;
import de.hsbremen.tc.tnc.message.m.serialize.ImWriter;

class PaAttributeFactoryDefaultPasswordEnabledValueWriter implements ImWriter<PaAttributeValueFactoryDefaultPasswordEnabled>{

	@Override
	public void write(final PaAttributeValueFactoryDefaultPasswordEnabled data, final OutputStream out)
			throws SerializationException {
		if(data == null){
			throw new NullPointerException("Value cannot be NULL.");
		}
		
		PaAttributeValueFactoryDefaultPasswordEnabled aValue = data;
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		/* factory default password status */
		byte[] result = Arrays.copyOfRange(ByteBuffer.allocate(8).putLong(aValue.getFactoryDefaultPasswordStatus().status()).array(),4,8);
		try {
			buffer.write(result);
		} catch (IOException e) {
			throw new SerializationException(
					"Status code could not be written to the buffer.", e, false,
					Long.toString(aValue.getFactoryDefaultPasswordStatus().status()));
		}

		try {
			buffer.writeTo(out);
		} catch (IOException e) {
			throw new SerializationException("Attribute could not be written to the OutputStream.",e, true);
		}
	}
}
