package org.ietf.nea.pb.validate.rules;

import org.ietf.nea.pb.message.enums.PbMessageErrorCodeEnum;
import org.ietf.nea.pb.validate.enums.PbErrorCauseEnum;

import de.hsbremen.tc.tnc.IETFConstants;
import de.hsbremen.tc.tnc.tnccs.exception.ValidationException;

public class LangCodeStringLimit {
	public static void check(final String value) throws ValidationException{
    	if(value != null && value.length() > IETFConstants.IETF_MAX_LANG_CODE_LENGTH){
            throw new ValidationException("Message language code is to large.",true,PbMessageErrorCodeEnum.IETF_INVALID_PARAMETER.code(),PbErrorCauseEnum.VALUE_TO_LARGE.number(),value);
        }
    }
}
