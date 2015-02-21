package org.ietf.nea.pb.message;

import org.ietf.nea.exception.RuleException;
import org.ietf.nea.pb.message.enums.PbMessageAccessRecommendationEnum;
import org.ietf.nea.pb.message.enums.PbMessageTlvFixedLengthEnum;
import org.ietf.nea.pb.validate.rules.AccessRecommendation;

public class PbMessageValueAccessRecommendationBuilderIetf implements
		PbMessageValueAccessRecommendationBuilder {
    
	private long length;
    private PbMessageAccessRecommendationEnum recommendation;  //16 bit(s)
	
	public PbMessageValueAccessRecommendationBuilderIetf(){
		this.length = PbMessageTlvFixedLengthEnum.ACC_REC_VALUE.length();
		this.recommendation = PbMessageAccessRecommendationEnum.ALLOWED;
	}

	@Override
	public PbMessageValueAccessRecommendationBuilder setRecommendation(int recommendation) throws RuleException {
		
		AccessRecommendation.check(recommendation);
		this.recommendation = PbMessageAccessRecommendationEnum.fromNumber(recommendation);
		
		return this;
	}

	@Override
	public PbMessageValueAccessRecommendation toObject(){
		
		return new PbMessageValueAccessRecommendation(this.length, this.recommendation);
	}

	@Override
	public PbMessageValueAccessRecommendationBuilder newInstance() {

		return new PbMessageValueAccessRecommendationBuilderIetf();
	}

}