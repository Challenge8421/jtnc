package de.hsbremen.tc.tnc.tnccs.im.handler;

import java.util.List;

import de.hsbremen.tc.tnc.report.ImvRecommendationPair;

public interface ImvHandler extends TnccsMessageHandler{

	public abstract List<ImvRecommendationPair> solicitRecommendation();
	
}