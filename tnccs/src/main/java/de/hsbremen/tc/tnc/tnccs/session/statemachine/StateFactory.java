package de.hsbremen.tc.tnc.tnccs.session.statemachine;

import de.hsbremen.tc.tnc.tnccs.session.base.state.TnccsContentHandler;
import de.hsbremen.tc.tnc.tnccs.session.statemachine.enums.TnccsStateEnum;

public interface StateFactory {

	public abstract State createState(TnccsStateEnum id,
			TnccsContentHandler contentHandler);

}