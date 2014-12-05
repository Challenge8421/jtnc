package de.hsbremen.tc.tnc.tnccs.session.statemachine.session;

import de.hsbremen.tc.tnc.attribute.Attributed;
import de.hsbremen.tc.tnc.tnccs.adapter.connection.DefaultImcConnectionAdapterFactory;
import de.hsbremen.tc.tnc.tnccs.adapter.connection.DefaultImcConnectionContext;
import de.hsbremen.tc.tnc.tnccs.adapter.connection.ImcConnectionAdapterFactory;
import de.hsbremen.tc.tnc.tnccs.adapter.connection.ImcConnectionContext;
import de.hsbremen.tc.tnc.tnccs.adapter.im.ImcAdapter;
import de.hsbremen.tc.tnc.tnccs.im.handler.DefaultImcHandler;
import de.hsbremen.tc.tnc.tnccs.im.handler.DefaultTnccHandler;
import de.hsbremen.tc.tnc.tnccs.im.handler.DefaultTnccsValidationExceptionHandler;
import de.hsbremen.tc.tnc.tnccs.im.handler.ImcHandler;
import de.hsbremen.tc.tnc.tnccs.im.handler.TnccHandler;
import de.hsbremen.tc.tnc.tnccs.im.handler.TnccsValidationExceptionHandler;
import de.hsbremen.tc.tnc.tnccs.im.manager.ImAdapterManager;
import de.hsbremen.tc.tnc.tnccs.session.base.AttributeCollection;
import de.hsbremen.tc.tnc.tnccs.session.base.SessionAttributes;
import de.hsbremen.tc.tnc.tnccs.session.base.state.DefaultTnccsContentHandler;
import de.hsbremen.tc.tnc.tnccs.session.base.state.TnccsContentHandler;
import de.hsbremen.tc.tnc.tnccs.session.connection.TnccsChannelFactory;
import de.hsbremen.tc.tnc.tnccs.session.connection.TnccsInputChannel;
import de.hsbremen.tc.tnc.tnccs.session.connection.TnccsOutputChannel;
import de.hsbremen.tc.tnc.tnccs.session.statemachine.DefaultClientStateFactory;
import de.hsbremen.tc.tnc.tnccs.session.statemachine.StateDriver;
import de.hsbremen.tc.tnc.tnccs.session.statemachine.StateMachine;
import de.hsbremen.tc.tnc.transport.connection.TransportConnection;

public class NewDefaultSessionFactory {

	private final ImAdapterManager<ImcAdapter> adapterManager;
	private final TnccsChannelFactory channelFactory;
	
	public NewDefaultSessionFactory(ImAdapterManager<ImcAdapter> adapterManager, TnccsChannelFactory channelFactory){
		this.adapterManager = adapterManager;
		this.channelFactory = channelFactory;
	}
	
	public SessionBase createTnccSession(TransportConnection connection){
		
		Session s = new Session(new SessionAttributes(this.channelFactory.getProtocol(), this.channelFactory.getVersion()));
		
		TnccsOutputChannel outChannel = this.channelFactory.createOutputChannel(connection); 
		TnccsInputChannel inChannel = this.channelFactory.createInputChannel(connection, s);
		
		AttributeCollection attributes = new AttributeCollection();
		attributes.add(s.getAttributes());
		
		Attributed connectionAttributes = connection.getAttributes();
		if(connectionAttributes != null){
			attributes.add(connectionAttributes);
		}
		
		ImcConnectionContext connectionContext = new DefaultImcConnectionContext(attributes,s);
		ImcConnectionAdapterFactory connectionFactory = new DefaultImcConnectionAdapterFactory(connectionContext);
		
		ImcHandler imcHandler = new DefaultImcHandler(adapterManager,connectionFactory, connectionContext,adapterManager.getRouter());
		TnccHandler tnccHandler = new DefaultTnccHandler();
		TnccsValidationExceptionHandler exceptionHandler = new DefaultTnccsValidationExceptionHandler();
		
		TnccsContentHandler contentHandler = new DefaultTnccsContentHandler(imcHandler, tnccHandler, exceptionHandler);
		
		StateMachine machine = new StateDriver(contentHandler, DefaultClientStateFactory.getInstance());
		
		// finalize session and run
		s.registerStatemachine(machine);
		s.registerInput(inChannel);
		s.registerOutput(outChannel);
		s.start(connection.isSelfInititated());

		
		return s;
		
	}
	
	
	
}
