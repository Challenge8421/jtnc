package de.hsbremen.tc.tnc.tnccs.im.handler;

import java.util.List;

import de.hsbremen.tc.tnc.report.ImvRecommendationPair;

public interface TncsHandler extends TnccsHandler{

	public abstract void provideRecommendation(List<ImvRecommendationPair> imvResults);
}