package org.ietf.nea.pb.validate;

import java.util.HashMap;
import java.util.Map;

import org.ietf.nea.pb.message.PbMessage;
import org.ietf.nea.pb.message.enums.PbMessageErrorCodeEnum;
import org.ietf.nea.pb.validate.rules.MessageTypNotReserved;
import org.ietf.nea.pb.validate.rules.VendorIdNotReserved;

import de.hsbremen.tc.tnc.tnccs.exception.ValidationException;
import de.hsbremen.tc.tnc.tnccs.validate.TnccsValidator;

public class PbMessageValidator implements TnccsValidator<PbMessage> {
	
	private Map<Long, Map<Long, TnccsValidator<PbMessage>>> pbMessageValidators;
    
    public PbMessageValidator(){
    	this.pbMessageValidators = new HashMap<>(); 
    }
    
    
    
    @Override
    public void validate(final PbMessage message) throws ValidationException {
        VendorIdNotReserved.check(message.getVendorId());
        MessageTypNotReserved.check(message.getType());
        
        long vendorId = message.getVendorId();
    	long messageType = message.getType();
    	if (this.pbMessageValidators.containsKey(vendorId)) {
			if (this.pbMessageValidators.get(vendorId)
					.containsKey(messageType)) {
				this.pbMessageValidators
						.get(vendorId).get(messageType).validate(message);
			}else{
				 throw new ValidationException("Message type is not supported.",false,PbMessageErrorCodeEnum.IETF_INVALID_PARAMETER.code(),Long.toString(message.getType()));
			}
		}else{
			 throw new ValidationException("Vendor ID is not supported.",false,PbMessageErrorCodeEnum.IETF_INVALID_PARAMETER.code(),Long.toString(message.getVendorId()));
		}
    }



	@Override
	public void addValidator(final Long vendorId, final Long messageType,
			final TnccsValidator<PbMessage> validator) {
		if(pbMessageValidators.containsKey(vendorId)){
			pbMessageValidators.get(vendorId).put(messageType, validator);
		}else{
			pbMessageValidators.put(vendorId, new HashMap<Long, TnccsValidator<PbMessage>>());
			pbMessageValidators.get(vendorId).put(messageType, validator);
		}
		
	}



	@Override
	public void removeValidator(final Long vendorId, final Long messageType) {
		if(pbMessageValidators.containsKey(vendorId)){
			if(pbMessageValidators.get(vendorId).containsKey(messageType)){
				pbMessageValidators.get(vendorId).remove(messageType);
			}
			if(pbMessageValidators.get(vendorId).isEmpty()){
				pbMessageValidators.remove(vendorId);
			}
		}
	}
}
