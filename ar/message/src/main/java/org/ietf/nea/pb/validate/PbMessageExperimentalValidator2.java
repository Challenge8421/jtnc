package org.ietf.nea.pb.validate;

import java.util.Arrays;

import org.ietf.nea.IETFConstants;
import org.ietf.nea.pb.message.PbMessage;
import org.ietf.nea.pb.message.enums.PbMessageErrorCodeEnum;
import org.ietf.nea.pb.message.enums.PbMessageFlagsEnum;
import org.ietf.nea.pb.message.enums.PbMessageTypeEnum;

import de.hsbremen.tc.tnc.tnccs.exception.ValidationException;
import de.hsbremen.tc.tnc.tnccs.validate.TnccsHeaderValidator;

public class PbMessageExperimentalValidator2 implements TnccsHeaderValidator<PbMessage> {
	
	private static final class Singleton{
		private static final PbMessageExperimentalValidator2 INSTANCE = new PbMessageExperimentalValidator2();  
	}  
	
	public static PbMessageExperimentalValidator2 getInstance(){
	   return Singleton.INSTANCE;
	}
 
    private PbMessageExperimentalValidator2() {
    	// Singleton
    }
    
    @Override
    public void validate(final PbMessage message) throws ValidationException {
    	if(message.getVendorId() != IETFConstants.IETF_PEN_VENDORID || message.getType() != PbMessageTypeEnum.IETF_PB_EXPERIMENTAL.messageType()){
    		throw new IllegalArgumentException("Unsupported message type with vendor ID "+ message.getVendorId() +" and message type "+ message.getType() +".");
    	}
        PbCommonMessageValidator2.getInstance().validate(message);
        this.checkNoSkipNotSet(message);
    }
    
    private void checkNoSkipNotSet(final PbMessage message) throws ValidationException {
        if(message.getFlags().contains(PbMessageFlagsEnum.NOSKIP)){
            throw new ValidationException("NOSKIP not allowed in this message.",true,PbMessageErrorCodeEnum.IETF_INVALID_PARAMETER.code(),Arrays.toString(message.getFlags().toArray()));
        }
    }

}
