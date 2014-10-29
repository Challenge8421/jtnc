package org.ietf.nea.pa.serialize.reader;

import java.io.IOException;
import java.io.InputStream;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pa.attribute.PaAttributeValueFactoryDefaultPasswordEnabled;
import org.ietf.nea.pa.attribute.PaAttributeValueFactoryDefaultPasswordEnabledBuilder;
import org.ietf.nea.pa.attribute.enums.PaAttributeTlvFixedLength;

import de.hsbremen.tc.tnc.exception.SerializationException;
import de.hsbremen.tc.tnc.exception.ValidationException;
import de.hsbremen.tc.tnc.m.serialize.ImReader;
import de.hsbremen.tc.tnc.util.ByteArrayHelper;

class PaAttributeFactoryDefaultPasswordEnabledValueReader implements ImReader<PaAttributeValueFactoryDefaultPasswordEnabled>{

	private PaAttributeValueFactoryDefaultPasswordEnabledBuilder builder;
	
	PaAttributeFactoryDefaultPasswordEnabledValueReader(PaAttributeValueFactoryDefaultPasswordEnabledBuilder builder){
		this.builder = builder;
	}
	
	@Override
	public PaAttributeValueFactoryDefaultPasswordEnabled read(final InputStream in, final long attributeLength)
			throws SerializationException, ValidationException {
		
		// ignore any given length and find out on your own.
		
		long errorOffset = 0;
		
		PaAttributeValueFactoryDefaultPasswordEnabled value = null;
		builder = (PaAttributeValueFactoryDefaultPasswordEnabledBuilder)builder.clear();

		try{
			
			try{
				
				int byteSize = 0;
				byte[] buffer = new byte[byteSize];
				
				/* status */
				byteSize = 4;
				buffer = ByteArrayHelper.arrayFromStream(in, byteSize);
				long status = ByteArrayHelper.toLong(buffer);
				this.builder.setStatus(status);
				errorOffset += byteSize;
				
			}catch (IOException e){
				throw new SerializationException(
						"Returned data for attribute value is to short or stream may be closed.", e, true);
			}

			value = (PaAttributeValueFactoryDefaultPasswordEnabled)builder.toValue();
			
		}catch (RuleException e){
			throw new ValidationException(e.getMessage(), e, errorOffset);
		}

		return value;
	}

	@Override
	public byte getMinDataLength() {
		return PaAttributeTlvFixedLength.FAC_PW.length();
	}

}