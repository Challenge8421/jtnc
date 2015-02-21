package org.ietf.nea.pb.serialize.reader.stream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pb.message.PbMessageValueLanguagePreference;
import org.ietf.nea.pb.message.PbMessageValueLanguagePreferenceBuilder;

import de.hsbremen.tc.tnc.message.exception.SerializationException;
import de.hsbremen.tc.tnc.message.exception.ValidationException;
import de.hsbremen.tc.tnc.message.tnccs.serialize.stream.TnccsReader;
import de.hsbremen.tc.tnc.message.util.ByteArrayHelper;
import de.hsbremen.tc.tnc.util.NotNull;

class PbMessageLanguagePreferenceValueReader implements TnccsReader<PbMessageValueLanguagePreference>{

	private PbMessageValueLanguagePreferenceBuilder baseBuilder;
	
	PbMessageLanguagePreferenceValueReader(PbMessageValueLanguagePreferenceBuilder builder){
		this.baseBuilder = builder;
	}
	
	@Override
	public PbMessageValueLanguagePreference read(final InputStream in, final long messageLength)
			throws SerializationException, ValidationException {
		
	    NotNull.check("Stream cannot be null.", in);
	    
		long errorOffset = 0;
		
		PbMessageValueLanguagePreference value = null;
		PbMessageValueLanguagePreferenceBuilder builder = (PbMessageValueLanguagePreferenceBuilder)this.baseBuilder.newInstance();

		try{
			
			try{
				// not needed here
				// int byteSize = 0;
				// byte[] buffer = new byte[byteSize];
				
				/* message */
				String languagePreference = readString(messageLength, in, Charset.forName("US-ASCII"));
				builder.setLanguagePreference(languagePreference);
				errorOffset += messageLength;
			
			}catch (IOException e){
				throw new SerializationException("Returned data for message value is to short or stream may be closed.",e,true);
			}

			value = (PbMessageValueLanguagePreference)builder.toObject();
			
		}catch (RuleException e){
			throw new ValidationException(e.getMessage(), e, errorOffset);
		}

		return value;
	}
	
	private String readString(final long length, final InputStream in, final Charset charset) throws IOException{
		
		String returnValue = "";

		byte[] buffer = new byte[0];
		byte[] temp = new byte[0];
		
		for(long l = length; l > 0; l -= buffer.length){

			buffer = ByteArrayHelper.arrayFromStream(in, ((l < 65535) ? (int)l : 65535));
			temp = ByteArrayHelper.mergeArrays(temp, Arrays.copyOfRange(buffer,0, buffer.length));

		}
		
		if(temp != null && temp.length > 0){
			returnValue = new String(temp, charset);
		}
		
		return returnValue;
	}


	@Override
	public byte getMinDataLength() {
	
		// no minimal length
		return 0; 
	}
}