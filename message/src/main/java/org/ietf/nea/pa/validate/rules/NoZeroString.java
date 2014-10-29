package org.ietf.nea.pa.validate.rules;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pa.attribute.enums.PaAttributeErrorCodeEnum;
import org.ietf.nea.pa.validate.enums.PaErrorCauseEnum;

public class NoZeroString {
	public static void check(final String value) throws RuleException{
    	if(value == null || value == ""){
            throw new RuleException("Attribute contains zero-string.",false,PaAttributeErrorCodeEnum.IETF_INVALID_PARAMETER.code(),PaErrorCauseEnum.ZERO_STRING.number(),value);
        }
    }
}
