package org.ietf.nea.pb.serialize.reader.bytebuffer;

import java.nio.BufferUnderflowException;
import java.nio.charset.Charset;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pb.message.enums.PbMessageTlvFixedLengthEnum;
import org.ietf.nea.pb.message.util.PbMessageValueRemediationParameterString;
import org.ietf.nea.pb.message.util.PbMessageValueRemediationParameterStringBuilder;

import de.hsbremen.tc.tnc.message.exception.SerializationException;
import de.hsbremen.tc.tnc.message.exception.ValidationException;
import de.hsbremen.tc.tnc.message.tnccs.serialize.bytebuffer.TnccsReader;
import de.hsbremen.tc.tnc.message.util.ByteBuffer;
import de.hsbremen.tc.tnc.util.NotNull;

class PbMessageRemediationParameterStringSubValueReader implements TnccsReader<PbMessageValueRemediationParameterString>{

	private PbMessageValueRemediationParameterStringBuilder baseBuilder;
	
	PbMessageRemediationParameterStringSubValueReader(PbMessageValueRemediationParameterStringBuilder builder){
		this.baseBuilder = builder;
	}
	
	@Override
	public PbMessageValueRemediationParameterString read(final ByteBuffer buffer, final long messageLength)
			throws SerializationException, ValidationException {
		
	    NotNull.check("Buffer cannot be null.", buffer);
	    
		long errorOffset = 0;
		
		PbMessageValueRemediationParameterString value = null;
		PbMessageValueRemediationParameterStringBuilder builder = (PbMessageValueRemediationParameterStringBuilder)baseBuilder.newInstance();

		try{
			
			try{
				
				/* first 4 bytes are the remediation string length */
				errorOffset = buffer.bytesRead();
				long reasonLength =  buffer.readLong((byte)4);
				
				/* remediation string */
				errorOffset = buffer.bytesRead();
				// FIXME this could lead to trouble because java can only handle a String of Integer.MAX_VALUE 
				// the length is shortened here and may switch to a negative value.
				byte[] sData = buffer.read((int)reasonLength);
				String reasonString = new String(sData, Charset.forName("UTF-8"));
				builder.setRemediationString(reasonString);
				
				
				/* next byte is the language code length */
				errorOffset = buffer.bytesRead();
				short langLength =  buffer.readShort((byte)1);
				
				/* language code */
				errorOffset = buffer.bytesRead();
				byte[] lsData = buffer.read(langLength);
				String langCode = new String(lsData, Charset.forName("US-ASCII"));
				builder.setLangCode(langCode);
			
			}catch (BufferUnderflowException e){
				throw new SerializationException("Data length " +buffer.bytesWritten()+ " in buffer to short.",e,true, Long.toString(buffer.bytesWritten()));
			}

			value = (PbMessageValueRemediationParameterString)builder.toObject();
			
		}catch (RuleException e){
			throw new ValidationException(e.getMessage(), e, errorOffset);
		}

		return value;
	}

	@Override
	public byte getMinDataLength() {
	
		return PbMessageTlvFixedLengthEnum.REA_STR_VALUE.length(); 
	}
}