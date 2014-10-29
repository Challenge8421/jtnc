package org.ietf.nea.pa.attribute;

import java.util.Date;
import java.util.List;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pa.attribute.enums.PaAttributeAssessmentResultEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeErrorCodeEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeFactoryDefaultPasswordStatusEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeForwardingStatusEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeOperationLastResultEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeOperationStatusEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeRemediationParameterTypeEnum;
import org.ietf.nea.pa.attribute.enums.PaAttributeTlvFixedLength;
import org.ietf.nea.pa.attribute.enums.PaAttributeTypeEnum;
import org.ietf.nea.pa.attribute.util.AttributeReference;
import org.ietf.nea.pa.attribute.util.PackageEntry;
import org.ietf.nea.pa.attribute.util.PortFilterEntry;
import org.ietf.nea.pa.message.PaMessageHeader;

import de.hsbremen.tc.tnc.IETFConstants;

public class PaAttributeFactoryIetf {

	private static final long VENDORID = IETFConstants.IETF_PEN_VENDORID;

	public static PaAttribute createAssessmentResult(final PaAttributeAssessmentResultEnum result) throws RuleException {

		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_ASSESSMENT_RESULT.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createAssessmentResultValue(result));

	}
	
	public static PaAttribute createFactoryDefaultPassword(final PaAttributeFactoryDefaultPasswordStatusEnum status) throws RuleException {

		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_FACTORY_DEFAULT_PW_ENABLED.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createFactoryDefaultPasswordValue(status));

	}
	
	public static PaAttribute createForwardingEnabled(final PaAttributeForwardingStatusEnum status) throws RuleException {

		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_FORWARDING_ENABLED.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createForwardingEnabledValue(status));

	}
	
	public static PaAttribute createProductInformation(long vendorId, int productId, String productName) throws RuleException{
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_PRODUCT_INFORMATION.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createProductInformationValue(vendorId, productId, productName));
	}
	
	public static PaAttribute createStringVersion(String versionNumber, String buildVersion, String configVersion) throws RuleException{
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_STRING_VERSION.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createStringVersionValue(versionNumber, buildVersion, configVersion));
	}
	
	public static PaAttribute createNumericVersion(final long majorVersion, final long minorVersion, final long buildVersion, final int servicePackMajor, final int servicePackMinor) throws RuleException {

		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_NUMERIC_VERSION.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createNumericVersionValue(majorVersion, minorVersion, buildVersion, servicePackMajor, servicePackMinor));

	}
	
	public static PaAttribute createInstalledPackages(List<PackageEntry> packages) throws RuleException{
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_INSTALLED_PACKAGES.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createInstalledPackagesValue(packages));
	}
	
	public static PaAttribute createOperationalStatus(final PaAttributeOperationStatusEnum status, final PaAttributeOperationLastResultEnum result, final Date lastUse) throws RuleException{
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_OPERATIONAL_STATUS.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createOperationalStatusValue(status, result, lastUse));
	}
	
	public static PaAttribute createPortFiler(PortFilterEntry first, PortFilterEntry... more ) throws RuleException{
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_PORT_FILTER.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createPortFilterValue(first, more));
	}
	
	public static PaAttribute createAttributeRequest(AttributeReference first, AttributeReference... more ) throws RuleException{
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_ATTRIBUTE_REQUEST.attributeType();
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createAttributeRequestValue(first, more));
	}
	
	public static PaAttribute createRemediationParameterString(final String remediationString, final String langCode) throws RuleException {

		long rpVendorId = VENDORID; 
		long rpType = PaAttributeRemediationParameterTypeEnum.IETF_STRING.type();
		
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_REMEDIATION_INSTRUCTIONS.attributeType(); 
	    
	    return createAttribute(flags, type, 
			  PaAttributeValueBuilderIetf.createRemediationParameterString(rpVendorId, rpType, remediationString, langCode));
		
	}
	
	public static PaAttribute createRemediationParameterUri(final String uri) throws RuleException {

		long rpVendorId = VENDORID; 
		long rpType = PaAttributeRemediationParameterTypeEnum.IETF_URI.type();
		
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_REMEDIATION_INSTRUCTIONS.attributeType(); 
	    
	    return createAttribute(flags, type, 
	    		PaAttributeValueBuilderIetf.createRemediationParameterUri(rpVendorId, rpType, uri));

	}
	
	public static PaAttribute createErrorInformationInvalidParam(final PaMessageHeader messageHeaderCopy, final long errorOffset ) throws RuleException {

		long errorVendorId = VENDORID; 
		long errorCode = PaAttributeErrorCodeEnum.IETF_INVALID_PARAMETER.code();
		
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_ERROR.attributeType(); 
	    
	    return createAttribute(flags, type, 
			  PaAttributeValueBuilderIetf.createErrorInformationInvalidParameter(errorVendorId, errorCode, messageHeaderCopy, errorOffset));
		
	}
	
	public static PaAttribute createErrorInformationUnsupportedVersion(final PaMessageHeader messageHeaderCopy, final short maxVersion , final short minVersion) throws RuleException {

		long errorVendorId = VENDORID; 
		long errorCode = PaAttributeErrorCodeEnum.IETF_UNSUPPORTED_VERSION.code();
		
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_ERROR.attributeType(); 
	    
	    return createAttribute(flags, type, 
			  PaAttributeValueBuilderIetf.createErrorInformationUnsupportedVersion(errorVendorId, errorCode, messageHeaderCopy, maxVersion, minVersion));
		
	}
	
	public static PaAttribute createErrorInformationUnsupportedAttribute(final PaMessageHeader messageHeaderCopy, final PaAttributeHeader attributeHeaderCopy) throws RuleException {

		long errorVendorId = VENDORID; 
		long errorCode = PaAttributeErrorCodeEnum.IETF_UNSUPPORTED_VERSION.code();
		
		byte flags = 0;
	    long type = PaAttributeTypeEnum.IETF_PA_ERROR.attributeType(); 
	    
	    return createAttribute(flags, type, 
			  PaAttributeValueBuilderIetf.createErrorInformationUnsupportedAttribute(errorVendorId, errorCode, messageHeaderCopy, attributeHeaderCopy));
	    
	}
	
	// TODO what do we do with errors
	private static PaAttribute createAttribute(final byte flags, final long type, final AbstractPaAttributeValue value) throws RuleException {
		if(value == null){
			throw new NullPointerException("Value cannot be null.");
		}
		
	    PaAttributeHeaderBuilderIetf aBuilder = new PaAttributeHeaderBuilderIetf();
		aBuilder.setFlags(flags);
		aBuilder.setVendorId(VENDORID);
		aBuilder.setType(type);
		aBuilder.setLength(PaAttributeTlvFixedLength.ATTRIBUTE.length() + value.getLength());

		PaAttribute attribute = new PaAttribute(aBuilder.toAttributeHeader(), value);
		
		return attribute;

	}
}