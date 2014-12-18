package org.ietf.nea.pa.serialize.reader.stream;

import java.io.IOException;
import java.io.InputStream;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pa.attribute.PaAttributeValueError;
import org.ietf.nea.pa.attribute.PaAttributeValueErrorBuilder;
import org.ietf.nea.pa.attribute.enums.PaAttributeErrorCodeEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeTlvFixedLengthEnum;
import org.ietf.nea.pa.attribute.util.PaAttributeValueErrorInformationInvalidParam;
import org.ietf.nea.pa.attribute.util.PaAttributeValueErrorInformationUnsupportedAttribute;
import org.ietf.nea.pa.attribute.util.PaAttributeValueErrorInformationUnsupportedVersion;

import de.hsbremen.tc.tnc.message.exception.SerializationException;
import de.hsbremen.tc.tnc.message.exception.ValidationException;
import de.hsbremen.tc.tnc.message.m.serialize.stream.ImReader;
import de.hsbremen.tc.tnc.message.util.ByteArrayHelper;

class PaAttributeErrorValueReader implements ImReader<PaAttributeValueError>{

	private PaAttributeValueErrorBuilder baseBuilder;
	
	// TODO should be a map to make the error parameters more customizable
	private final PaAttributeErrorInformationInvalidParamValueReader invalidParamReader;
	private final PaAttributeErrorInformationUnsupportedVersionValueReader unsupportedVersionReader;
	private final PaAttributeErrorInformationUnsupportedAttributeValueReader unsupportedAttributeReader;
	
	public PaAttributeErrorValueReader(PaAttributeValueErrorBuilder builder, PaAttributeErrorInformationInvalidParamValueReader invalidParamReader, PaAttributeErrorInformationUnsupportedVersionValueReader unsupportedVersionReader, PaAttributeErrorInformationUnsupportedAttributeValueReader unsupportedAttributeReader){
		this.baseBuilder = builder;
		this.invalidParamReader = invalidParamReader;
		this.unsupportedAttributeReader = unsupportedAttributeReader;
		this.unsupportedVersionReader = unsupportedVersionReader;
	}
	
	@Override
	public PaAttributeValueError read(final InputStream in, final long messageLength)
			throws SerializationException, ValidationException {
		
		long errorOffset = 0;
		
		PaAttributeValueError value = null;
		PaAttributeValueErrorBuilder builder = (PaAttributeValueErrorBuilder)this.baseBuilder.newInstance();

		long errorVendorId = 0L;
		long errorCode = 0L;
		
		try{
			
			try{
				
				int byteSize = 0;
				byte[] buffer = new byte[byteSize];
				
				/* ignore reserved */
				byteSize = 1;
				ByteArrayHelper.arrayFromStream(in, byteSize);
				errorOffset += byteSize;
				
				/* vendor ID */
				byteSize = 3;
				buffer = ByteArrayHelper.arrayFromStream(in, byteSize);
				errorVendorId = ByteArrayHelper.toLong(buffer);
				builder.setErrorVendorId(errorVendorId);
				errorOffset += byteSize;
				
				/* code */
				byteSize = 4;
				buffer = ByteArrayHelper.arrayFromStream(in, byteSize);
				errorCode = ByteArrayHelper.toLong(buffer);
				builder.setErrorCode(errorCode);
				errorOffset += byteSize;
			
			}catch (IOException e){
				throw new SerializationException(
						"Returned data for message value is to short or stream may be closed.", e, true);
			}
			
			
			/* error parameter */
			// value length = header length - overall message length
			long valueLength = messageLength - errorOffset;

			try{
				if(errorCode == PaAttributeErrorCodeEnum.IETF_INVALID_PARAMETER.code()){ 
					
					PaAttributeValueErrorInformationInvalidParam errorInformation = this.invalidParamReader.read(in, valueLength);
					builder.setErrorInformation(errorInformation);
					
				}else if(errorCode == PaAttributeErrorCodeEnum.IETF_UNSUPPORTED_VERSION.code()){
					
					PaAttributeValueErrorInformationUnsupportedVersion errorInformation = this.unsupportedVersionReader.read(in, valueLength);
					builder.setErrorInformation(errorInformation);
					
				}else if(errorCode == PaAttributeErrorCodeEnum.IETF_UNSUPPORTED_MANDATORY_ATTRIBUTE.code()){
				
					PaAttributeValueErrorInformationUnsupportedAttribute errorInformation = this.unsupportedAttributeReader.read(in, valueLength);
					builder.setErrorInformation(errorInformation);
				
				}else{
					try{
						// skip the remaining bytes of the message
						in.skip(valueLength);
					}catch (IOException e1){
						throw new SerializationException("Bytes from InputStream could not be skipped, stream seems closed.", true);
					}
					return null;
				}
			}catch(ValidationException e){
				// catch exception and add throw with recalculated offset, pass on the rule exception
				throw new ValidationException(e.getMessage(), e.getCause(), e.getExceptionOffset() + errorOffset);
			}

			value = (PaAttributeValueError)builder.toObject();
			
		}catch (RuleException e){
			throw new ValidationException(e.getMessage(), e, errorOffset);
		}

		return value;
	}

	@Override
	public byte getMinDataLength() {
		return PaAttributeTlvFixedLengthEnum.ERR_INF.length();
	}

}