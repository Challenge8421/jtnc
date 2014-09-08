package org.ietf.nea.pb.validate.rules;

import java.net.URI;
import java.net.URISyntaxException;

import org.ietf.nea.pb.message.enums.PbMessageErrorCodeEnum;

import de.hsbremen.tc.tnc.tnccs.exception.ValidationException;

public class UriSyntax {

	public static void check(String uri) throws ValidationException{
		if(uri == null){
			throw new NullPointerException("URI string cannot be null.");
		}
		try {
			new URI(uri);
		} catch (URISyntaxException e) {
			throw new ValidationException("URI syntax not valid.",e, true, PbMessageErrorCodeEnum.IETF_INVALID_PARAMETER.code(),uri);
		}
		
		
	}
	
}
