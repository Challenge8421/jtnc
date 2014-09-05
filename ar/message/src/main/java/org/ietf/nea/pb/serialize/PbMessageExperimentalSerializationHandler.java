package org.ietf.nea.pb.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.ietf.nea.pb.message.PbMessageValueExperimental;
import org.ietf.nea.pb.serialize.util.ByteArrayHelper;

import de.hsbremen.tc.tnc.tnccs.exception.SerializationException;
import de.hsbremen.tc.tnc.tnccs.serialize.TnccsSerializationHandler;

public class PbMessageExperimentalSerializationHandler implements TnccsSerializationHandler<PbMessageValueExperimental> {

	private static final int MESSAGE_VALUE_FIXED_SIZE = 0;
	
	private static final class Singleton{
		private static final PbMessageExperimentalSerializationHandler INSTANCE = new  PbMessageExperimentalSerializationHandler();  
	}
	public static  PbMessageExperimentalSerializationHandler getInstance(){
	    	return Singleton.INSTANCE;
	}
	    
	private  PbMessageExperimentalSerializationHandler(){
	    	// Singleton
	}
	
	
	@Override
	public void encode(final PbMessageValueExperimental data, final OutputStream out)
			throws SerializationException {

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		/* Message */
		try{
			buffer.write(data.getMessage().getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			throw new SerializationException(
					"Message could not be written to the buffer.", e,
					data.getMessage());
		}
		try {
			buffer.writeTo(out);
		} catch (IOException e) {
			throw new SerializationException("Message could not be written to the OutputStream.",e);
		}
	}

	@Override
	public PbMessageValueExperimental decode(final InputStream in, final long length) throws SerializationException {
		PbMessageValueExperimental value = null; 	
		
		if(length <= 0){
			return value;
		}
		
		long messageLength = length;
		
		byte[] buffer = new byte[MESSAGE_VALUE_FIXED_SIZE];

		int count = 0;
		String content = "";
		
		count = 0;
		byte[] temp = new byte[0];
		for(long l = messageLength; l > 0; l -= count){
			
			buffer = (l < 65535) ? new byte[(int)l] : new byte[65535];
			try {
				count = in.read(buffer);
			} catch (IOException e) {
				throw new SerializationException(
						"InputStream could not be read.", e);
			}
			temp = ByteArrayHelper.mergeArrays(temp, Arrays.copyOfRange(buffer, 0, count));
		}
		if(temp != null && temp.length > 0){
			content= new String(temp, Charset.forName("UTF-8"));
		}
		
		value = new PbMessageValueExperimental(content);
		
		return value;
	}
}
