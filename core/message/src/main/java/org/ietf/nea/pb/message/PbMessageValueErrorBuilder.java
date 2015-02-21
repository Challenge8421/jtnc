package org.ietf.nea.pb.message;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pb.message.util.AbstractPbMessageValueErrorParameter;

import de.hsbremen.tc.tnc.message.tnccs.message.TnccsMessageValueBuilder;

public interface PbMessageValueErrorBuilder extends TnccsMessageValueBuilder {

	/**
	 * @param errorFlags the errorFlags to set
	 */
	public abstract PbMessageValueErrorBuilder setErrorFlags(byte errorFlags);

	/**
	 * @param errorVendorId the errorVendorId to set
	 * @throws RuleException 
	 */
	public abstract PbMessageValueErrorBuilder setErrorVendorId(long errorVendorId)
			throws RuleException;

	/**
	 * @param errorCode the errorCode to set
	 * @throws RuleException 
	 */
	public abstract PbMessageValueErrorBuilder setErrorCode(int errorCode)
			throws RuleException;

	/**
	 * @param errorParameter the errorParameter to set
	 */
	public abstract PbMessageValueErrorBuilder setErrorParameter(AbstractPbMessageValueErrorParameter errorParameter);

}