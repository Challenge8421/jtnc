package de.hsbremen.tc.tnc.message.tnccs.message;

import de.hsbremen.tc.tnc.message.tnccs.TnccsData;

public interface TnccsMessageHeader extends TnccsData{
	
	public long getVendorId();
	
	public long getMessageType();
	
}