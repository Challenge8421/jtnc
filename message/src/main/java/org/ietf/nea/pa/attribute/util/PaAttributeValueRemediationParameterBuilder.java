package org.ietf.nea.pa.attribute.util;

import org.ietf.nea.exception.RuleException;

public interface PaAttributeValueRemediationParameterBuilder {

	public abstract AbstractPaAttributeValueRemediationParameter toValue() throws RuleException;
	
	public abstract PaAttributeValueRemediationParameterBuilder clear();
}