package de.hsbremen.tc.tnc.tnccs.session.statemachine.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsbremen.tc.tnc.tnccs.message.handler.TnccContentHandler;
import de.hsbremen.tc.tnc.tnccs.session.statemachine.State;
import de.hsbremen.tc.tnc.tnccs.session.statemachine.StateHelper;
import de.hsbremen.tc.tnc.tnccs.session.statemachine.enums.TnccsStateEnum;

public class DefaultClientStateFactory implements StateHelper<TnccContentHandler>{

	protected static final Logger LOGGER = LoggerFactory.getLogger(DefaultClientStateFactory.class);
	private final TnccContentHandler contentHandler;
	
	public DefaultClientStateFactory(TnccContentHandler contentHandler){
		if(contentHandler == null){
			throw new NullPointerException("Content handler cannot be null.");
		}
		this.contentHandler = contentHandler;
	}

	
	
	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.tnccs.session.statemachine.StateHelper#getHander()
	 */
	@Override
	public TnccContentHandler getHandler() {
		return this.contentHandler;
	}



	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.tnccs.session.statemachine.StateFactory#createState(de.hsbremen.tc.tnc.tnccs.session.statemachine.enums.TnccsStateEnum, de.hsbremen.tc.tnc.tnccs.session.base.state.TnccsContentHandler)
	 */
	@Override
	public State createState(TnccsStateEnum id){
		
		State t = null;
		switch(id){
		case CLIENT_WORKING:
			t = new DefaultClientClientWorkingState(this);
			break;
		case DECIDED:
			t = new DefaultClientDecidedState(this);
			break;
		case END:
			t = new DefaultCommonEndState(false, this.contentHandler);
			break;
		case ERROR:
			t = new DefaultCommonErrorState(false, this);
			break;
		case INIT:
			t = new DefaultClientInitState(this);
			break;
		case RETRY:
			t = new DefaultClientRetryState(this);
			break;
		case SERVER_WORKING:
			t = new DefaultClientServerWorkingState(this);
			break;
		}
		if(t != null){
			LOGGER.debug(t.getClass().getCanonicalName() + " created.");
			return t;
		}else{
			throw new IllegalArgumentException("The implementation of the state with id " + id.value() +" is unknown.");
		}
	}	
}
