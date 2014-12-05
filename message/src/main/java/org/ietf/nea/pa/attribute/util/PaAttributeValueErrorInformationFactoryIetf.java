package org.ietf.nea.pa.attribute.util;

import java.util.Arrays;

import org.ietf.nea.pa.attribute.PaAttributeHeader;
import org.ietf.nea.pa.attribute.RawMessageHeader;
import org.ietf.nea.pa.attribute.enums.PaAttributeErrorCodeEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeTlvFixedLength;

import de.hsbremen.tc.tnc.IETFConstants;
import de.hsbremen.tc.tnc.message.util.ByteArrayHelper;

public class PaAttributeValueErrorInformationFactoryIetf {
	
	public static PaAttributeValueErrorInformationInvalidParam createErrorInformationInvalidParameter(long errorVendorId, long errorCode, byte[] messageHeader, long offset){
		
		if(messageHeader == null){
			throw new NullPointerException("Message header cannot be null.");
		}
		
		if(offset < 0){
			throw new IllegalArgumentException("Offset cannot be negative.");
		}
		
		if(errorVendorId != IETFConstants.IETF_PEN_VENDORID || errorCode != PaAttributeErrorCodeEnum.IETF_INVALID_PARAMETER.code()){
			throw new IllegalArgumentException("Requested error value is not supported in attribute with error vendor ID "+ errorVendorId +" and code "+ errorCode +".");
		}
		
		RawMessageHeader header = parseHeader(messageHeader);
		
		long length = PaAttributeTlvFixedLength.MESSAGE.length() + 4; // 4 = offset length
		
		return new PaAttributeValueErrorInformationInvalidParam(length, header, offset);
	}

	public static PaAttributeValueErrorInformationUnsupportedAttribute createErrorInformationUnsupportedAttribute(long errorVendorId, long errorCode, byte[] messageHeader, PaAttributeHeader attributeHeader){
		
		if(messageHeader == null){
			throw new NullPointerException("Message header cannot be null.");
		}
		
		if(attributeHeader == null){
			throw new NullPointerException("Attribute header cannot be null.");
		}
		
		if(errorVendorId != IETFConstants.IETF_PEN_VENDORID || errorCode != PaAttributeErrorCodeEnum.IETF_UNSUPPORTED_MANDATORY_ATTRIBUTE.code()){
			throw new IllegalArgumentException("Requested error value is not supported in attribute with error vendor ID "+ errorVendorId +" and code "+ errorCode +".");
		}
		
		RawMessageHeader header = parseHeader(messageHeader);
		
		long length = PaAttributeTlvFixedLength.MESSAGE.length() + PaAttributeTlvFixedLength.ATTRIBUTE.length() - 4; // - 4 = attribute length is ignored
		
		return new PaAttributeValueErrorInformationUnsupportedAttribute(length, header, attributeHeader);
	}
	
	public static PaAttributeValueErrorInformationUnsupportedVersion createErrorInformationUnsupportedVersion(long errorVendorId, long errorCode, byte[] messageHeader, short maxVersion, short minVersion){
		
		if(messageHeader == null){
			throw new NullPointerException("Message header cannot be null.");
		}

		if(errorVendorId != IETFConstants.IETF_PEN_VENDORID || errorCode != PaAttributeErrorCodeEnum.IETF_UNSUPPORTED_VERSION.code()){
			throw new IllegalArgumentException("Requested error value is not supported in attribute with error vendor ID "+ errorVendorId +" and code "+ errorCode +".");
		}
		
		RawMessageHeader header = parseHeader(messageHeader);
		
		long length = PaAttributeTlvFixedLength.MESSAGE.length() + 4; // 4 = min+max version length
		
		return new PaAttributeValueErrorInformationUnsupportedVersion(length, header, maxVersion, minVersion);
	}
	
	private static RawMessageHeader parseHeader(byte[] messageHeader) {
		
		byte[] sizedMessageHeader = Arrays.copyOf(messageHeader, PaAttributeTlvFixedLength.MESSAGE.length());
		
		short version = sizedMessageHeader[0];
		byte[] reserved = Arrays.copyOfRange(sizedMessageHeader, 1,4);
		long identifier = ByteArrayHelper.toLong(Arrays.copyOfRange(sizedMessageHeader, 4,8));
		
	    return new RawMessageHeader(version, reserved, identifier);
	}
}
