package org.ietf.nea.pb.message.util;

import java.net.URI;

public class PbMessageValueRemediationParameterUri extends AbstractPbMessageValueRemediationParameter{

//    //overload the superclass default vendorId, type and length value
//    protected final long rpVendorId = IETFConstants.IETF_PEN_VENDORID; // 24 bit(s)
//    protected final long rpType = PbMessageRemediationParameterTypeEnum.IETF_URI.type();         // 32 bit(s)
  
    private final URI remediationUri; //variable length


	PbMessageValueRemediationParameterUri(final long length, final URI remediationUri) {
		super(length);
		this.remediationUri = remediationUri;
	}

	/**
	 * @return the remediationUri
	 */
	public URI getRemediationUri() {
		return this.remediationUri;
	}

    
    
    
}