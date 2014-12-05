package de.hsbremen.tc.tnc.tnccs.session.base.state;

import java.util.List;

import de.hsbremen.tc.tnc.connection.TncConnectionState;
import de.hsbremen.tc.tnc.message.exception.ValidationException;
import de.hsbremen.tc.tnc.message.tnccs.message.TnccsMessage;

public interface TnccsContentHandler {

	public abstract void setConnectionState(TncConnectionState state);
	
	public abstract List<TnccsMessage> collectMessages();

	public abstract List<TnccsMessage> handleMessages(List<? extends TnccsMessage> list);
	
	public abstract List<TnccsMessage> handleExceptions(List<ValidationException> exceptions);

	public abstract TncConnectionState getAccessDecision();
}