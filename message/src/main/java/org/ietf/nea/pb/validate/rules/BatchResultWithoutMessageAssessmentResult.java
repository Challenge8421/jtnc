package org.ietf.nea.pb.validate.rules;

import java.util.List;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pb.batch.enums.PbBatchTypeEnum;
import org.ietf.nea.pb.message.PbMessage;
import org.ietf.nea.pb.message.enums.PbMessageErrorCodeEnum;
import org.ietf.nea.pb.message.enums.PbMessageTypeEnum;
import org.ietf.nea.pb.validate.enums.PbErrorCauseEnum;

import de.hsbremen.tc.tnc.IETFConstants;

public class BatchResultWithoutMessageAssessmentResult {

	public static void check(final PbBatchTypeEnum type, List<PbMessage> messages) throws RuleException{
		if(type != PbBatchTypeEnum.RESULT){
			return;
		}
		if(messages != null){
			for (PbMessage pbMessage : messages) {
				if(pbMessage.getHeader().getVendorId() == IETFConstants.IETF_PEN_VENDORID && pbMessage.getHeader().getMessageType() ==  PbMessageTypeEnum.IETF_PB_ASSESSMENT_RESULT.messageType()){
					return;
				}
			}
		}
		throw new RuleException("The batch of type " + type.toString() + " must contain a message with vendor id " + IETFConstants.IETF_PEN_VENDORID + " and message type " + PbMessageTypeEnum.IETF_PB_ASSESSMENT_RESULT.messageType() + ".", true, PbMessageErrorCodeEnum.IETF_INVALID_PARAMETER.code(), PbErrorCauseEnum.BATCH_RESULT_NO_ASSESSMENT_RESULT.number(), 
				type.toString(), Long.toString(IETFConstants.IETF_PEN_VENDORID), PbMessageTypeEnum.IETF_PB_ASSESSMENT_RESULT.toString());
	}
}
