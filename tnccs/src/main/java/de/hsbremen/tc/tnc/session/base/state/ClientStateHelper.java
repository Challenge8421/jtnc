package de.hsbremen.tc.tnc.session.base.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ietf.nea.pb.batch.PbBatch;
import org.ietf.nea.pb.batch.PbBatchFactoryIetf;
import org.ietf.nea.pb.message.PbMessageFactoryIetf;
import org.ietf.nea.pb.message.enums.PbMessageErrorCodeEnum;
import org.ietf.nea.pb.message.enums.PbMessageErrorFlagsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hsbremen.tc.tnc.exception.ValidationException;
import de.hsbremen.tc.tnc.session.base.state.StateContext;
import de.hsbremen.tc.tnc.tnccs.batch.TnccsBatch;
import de.hsbremen.tc.tnc.tnccs.message.TnccsMessage;
import de.hsbremen.tc.tnc.tnccs.serialize.TnccsBatchContainer;

abstract class ClientStateHelper {

	private ClientStateHelper(){
		throw new IllegalAccessError("Class is abstract and static.");
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientStateHelper.class);

	static SessionState handleClose(TnccsContentHandler handler, StateContext context ,TnccsBatchContainer batchContainer) {
		PbBatch b = (PbBatch) batchContainer.getResult();
		if(b.getMessages() != null){
			handler.handleMessages(b.getMessages());
		}
		
		if(batchContainer.getExceptions() != null){
			handler.handleExceptions(batchContainer.getExceptions());
		}
		SessionState successor = new EndState(handler);
		successor.handle(context);
		
		return successor;
	}

	static TnccsBatch createCloseBatch(TnccsMessage...error) {
		
		List<TnccsMessage> messages = (error != null) ? Arrays.asList(error) : new ArrayList<TnccsMessage>();
		TnccsBatch b = null;
		try {
			b = PbBatchFactoryIetf.createClientClose(messages);
		} catch (ValidationException e) {
			LOGGER.error("Error while creating a close batch, transitioning to end state, without close." ); 
		}
		
		return b;
	}

	static TnccsMessage createUnexpectedStateError() {
		try {
			return PbMessageFactoryIetf.createErrorSimple(new PbMessageErrorFlagsEnum[]{PbMessageErrorFlagsEnum.FATAL}, PbMessageErrorCodeEnum.IETF_UNEXPECTED_BATCH_TYPE);
		} catch (ValidationException e) {
			return null;
		}
	}
	
	static TnccsMessage createUnsupportedVersionError(short badVersion, short minVersion, short maxVersion) {
		try {
			return PbMessageFactoryIetf.createErrorVersion(new PbMessageErrorFlagsEnum[]{PbMessageErrorFlagsEnum.FATAL}, PbMessageErrorCodeEnum.IETF_UNSUPPORTED_VERSION,  badVersion, maxVersion, minVersion);
		} catch (ValidationException e) {
			return null;
		}
	}
	
	static TnccsMessage createLocalError() {
		try {
			return PbMessageFactoryIetf.createErrorSimple(new PbMessageErrorFlagsEnum[]{PbMessageErrorFlagsEnum.FATAL}, PbMessageErrorCodeEnum.IETF_LOCAL);
		} catch (ValidationException e) {
			return null;
		}
	}
}