package org.ietf.nea.pb.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.ietf.nea.pb.message.PbMessageValueAssessmentResult;
import org.ietf.nea.pb.message.PbMessageValueAssessmentResultBuilder;
import org.ietf.nea.pb.serialize.util.ByteArrayHelper;

import de.hsbremen.tc.tnc.tnccs.exception.SerializationException;
import de.hsbremen.tc.tnc.tnccs.exception.ValidationException;
import de.hsbremen.tc.tnc.tnccs.serialize.TnccsSerializer;

public class PbMessageAssessmentResultSerializer implements TnccsSerializer<PbMessageValueAssessmentResult> {

	private static final int MESSAGE_VALUE_FIXED_SIZE = PbMessageValueAssessmentResult.FIXED_LENGTH;
	
	private PbMessageValueAssessmentResultBuilder builder;
	
	public PbMessageAssessmentResultSerializer(PbMessageValueAssessmentResultBuilder builder){
	    this.builder = builder;
	}
	
	
	@Override
	public void encode(final PbMessageValueAssessmentResult data, final OutputStream out)
			throws SerializationException {	

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		/* Result */
		byte[] result = Arrays.copyOfRange(ByteBuffer.allocate(8).putLong(data.getResult().number()).array(),4,8);
		try {
			buffer.write(result);
		} catch (IOException e) {
			throw new SerializationException(
					"Result code could not be written to the buffer.", e,
					Long.toString(data.getResult().number()));
		}
		
		try {
			buffer.writeTo(out);
		} catch (IOException e) {
			throw new SerializationException("Message could not be written to the OutputStream.",e);
		}
	}

	@Override
	public PbMessageValueAssessmentResult decode(final InputStream in, final long length) throws SerializationException, ValidationException {
		PbMessageValueAssessmentResult value = null; 	
		this.builder.clear();
		// ignore any given length and find out on your own.

		byte[] buffer = new byte[MESSAGE_VALUE_FIXED_SIZE];

		int count = 0;
		// wait till data is available
		while (count == 0) {
			try {
				count = in.read(buffer);
			} catch (IOException e) {
				throw new SerializationException(
						"InputStream could not be read.", e);
			}
		}

		
		if (count >= MESSAGE_VALUE_FIXED_SIZE){

			/* Assessment result */
			long code = ByteArrayHelper.toLong(Arrays.copyOfRange(buffer, 0, MESSAGE_VALUE_FIXED_SIZE));
			
			this.builder.setResult(code);
			value = (PbMessageValueAssessmentResult)this.builder.toValue();

		} else {
			throw new SerializationException("Returned data length (" + count
					+ ") for message is to short or stream may be closed.",
					Integer.toString(count));
		}
		
		return value;
	}
}
