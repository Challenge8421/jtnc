package de.hsbremen.tc.tnc.tnccs.im.handler;

import de.hsbremen.tc.tnc.connection.TncConnectionState;

public interface TnccsHandler extends TnccsMessageHandler {

	public abstract TncConnectionState getAccessDecision();
	
}