package org.ietf.nea.pb.message;

import org.ietf.nea.pb.message.enums.PbMessageErrorCodeEnum;
import org.ietf.nea.pb.message.enums.PbMessageErrorFlagsEnum;
import org.ietf.nea.pb.validate.rules.ErrorCodeLimits;
import org.ietf.nea.pb.validate.rules.ErrorVendorIdLimits;

import de.hsbremen.tc.tnc.IETFConstants;
import de.hsbremen.tc.tnc.tnccs.exception.ValidationException;
import de.hsbremen.tc.tnc.tnccs.message.TnccsMessageValue;
import de.hsbremen.tc.tnc.tnccs.message.TnccsMessageValueBuilder;

public class PbMessageValueErrorBuilderIetf implements PbMessageValueErrorBuilder{

	private static final short RESERVED = 0;
	
	private PbMessageErrorFlagsEnum[] errorFlags; //  8 bit(s) 
    private long errorVendorId;                                           // 24 bit(s)
    private short errorCode;                                                // 16 bit(s)
    private byte[] errorParameter; //32 bit(s) , may be (1) (one field full 32 bit length) if offset or (4) (4 fields every field has 8 bit length) if version information is needed.
    
    public PbMessageValueErrorBuilderIetf(){
    	this.errorFlags = new PbMessageErrorFlagsEnum[0];
    	this.errorVendorId = IETFConstants.IETF_PEN_VENDORID;
    	this.errorCode = PbMessageErrorCodeEnum.IETF_LOCAL.code();
    	this.errorParameter = new byte[0];
    }

	/* (non-Javadoc)
	 * @see org.ietf.nea.pb.message.PbMessageValueErrorBuilder#setErrorFlags(byte)
	 */
	@Override
	public void setErrorFlags(byte errorFlags) {
		if ((errorFlags & 0x80)  == PbMessageErrorFlagsEnum.FATAL.bit()) {
			this.errorFlags = new PbMessageErrorFlagsEnum[]{PbMessageErrorFlagsEnum.FATAL};
		}
	}

	/* (non-Javadoc)
	 * @see org.ietf.nea.pb.message.PbMessageValueErrorBuilder#setErrorVendorId(long)
	 */
	@Override
	public void setErrorVendorId(long errorVendorId) throws ValidationException {
		
		ErrorVendorIdLimits.check(errorVendorId);
		this.errorVendorId = errorVendorId;
	}

	/* (non-Javadoc)
	 * @see org.ietf.nea.pb.message.PbMessageValueErrorBuilder#setErrorCode(short)
	 */
	@Override
	public void setErrorCode(short errorCode) throws ValidationException {
		
		ErrorCodeLimits.check(errorCode);
		this.errorCode = errorCode;
	}

	/* (non-Javadoc)
	 * @see org.ietf.nea.pb.message.PbMessageValueErrorBuilder#setErrorParameter(byte[])
	 */
	@Override
	public void setErrorParameter(byte[] errorParameter) {
		if( errorParameter != null){
			this.errorParameter = errorParameter;
		}
	}

	@Override
	public TnccsMessageValue toValue() throws ValidationException {

		return new PbMessageValueError(errorFlags, errorVendorId, errorCode, RESERVED, errorParameter);
	}

	@Override
	public TnccsMessageValueBuilder clear() {

		return new PbMessageValueErrorBuilderIetf();
	}

}
