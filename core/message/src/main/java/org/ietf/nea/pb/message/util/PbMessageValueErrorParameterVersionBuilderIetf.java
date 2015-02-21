package org.ietf.nea.pb.message.util;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pb.message.enums.PbMessageTlvFixedLengthEnum;

public class PbMessageValueErrorParameterVersionBuilderIetf implements PbMessageValueErrorParameterVersionBuilder{

	private long length;
    private short badVersion;
    private short maxVersion;
    private short minVersion;
    
    public PbMessageValueErrorParameterVersionBuilderIetf(){
    	this.length = PbMessageTlvFixedLengthEnum.ERR_SUB_VALUE.length();
    	this.badVersion = 0;
    	this.maxVersion = 0;
    	this.minVersion = 0;
    }

	@Override
	public PbMessageValueErrorParameterVersionBuilder setBadVersion(short version)
			throws RuleException {
		this.badVersion = version;
		return this;
	}

	@Override
	public PbMessageValueErrorParameterVersionBuilder setMaxVersion(short version)
			throws RuleException {
		this.maxVersion = version;
		return this;
	}

	@Override
	public PbMessageValueErrorParameterVersionBuilder setMinVersion(short version)
			throws RuleException {
		this.minVersion = version;
		return this;
	}

	@Override
	public PbMessageValueErrorParameterVersion toObject() throws RuleException {

		return new PbMessageValueErrorParameterVersion(length, badVersion, maxVersion, minVersion);
	}

	@Override
	public PbMessageValueErrorParameterVersionBuilder newInstance() {

		return new PbMessageValueErrorParameterVersionBuilderIetf();
	}

}