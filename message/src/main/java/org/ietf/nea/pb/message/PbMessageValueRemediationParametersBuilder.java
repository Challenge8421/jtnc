package org.ietf.nea.pb.message;

import de.hsbremen.tc.tnc.tnccs.exception.ValidationException;
import de.hsbremen.tc.tnc.tnccs.message.TnccsMessageValueBuilder;

public interface PbMessageValueRemediationParametersBuilder extends TnccsMessageValueBuilder {

	/**
	 * @param rpVendorId the rpVendorId to set
	 * @throws ValidationException 
	 */
	public abstract void setRpVendorId(long rpVendorId)
			throws ValidationException;

	/**
	 * @param rpType the rpType to set
	 * @throws ValidationException 
	 */
	public abstract void setRpType(long rpType) throws ValidationException;

	/**
	 * @param parameter the parameter to set
	 */
	public abstract void setParameter(
			AbstractPbMessageValueRemediationParametersValue parameter);

}