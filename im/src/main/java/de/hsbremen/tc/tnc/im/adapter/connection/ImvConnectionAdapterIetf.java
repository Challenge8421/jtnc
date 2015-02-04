package de.hsbremen.tc.tnc.im.adapter.connection;

import java.util.LinkedList;
import java.util.List;

import org.ietf.nea.pa.attribute.PaAttribute;
import org.ietf.nea.pa.message.PaMessageFactoryIetf;
import org.trustedcomputinggroup.tnc.ifimv.IMVConnection;
import org.trustedcomputinggroup.tnc.ifimv.IMVConnectionLong;
import org.trustedcomputinggroup.tnc.ifimv.TNCConstants;
import org.trustedcomputinggroup.tnc.ifimv.TNCException;

import de.hsbremen.tc.tnc.HSBConstants;
import de.hsbremen.tc.tnc.attribute.TncAttributeType;
import de.hsbremen.tc.tnc.exception.TncException;
import de.hsbremen.tc.tnc.exception.enums.TncExceptionCodeEnum;
import de.hsbremen.tc.tnc.im.adapter.data.ImObjectComponent;
import de.hsbremen.tc.tnc.im.adapter.data.enums.ImComponentFlagsEnum;
import de.hsbremen.tc.tnc.message.exception.SerializationException;
import de.hsbremen.tc.tnc.message.exception.ValidationException;
import de.hsbremen.tc.tnc.message.m.attribute.ImAttribute;
import de.hsbremen.tc.tnc.message.m.message.ImMessage;
import de.hsbremen.tc.tnc.message.m.serialize.bytebuffer.ImWriter;
import de.hsbremen.tc.tnc.message.util.ByteBuffer;
import de.hsbremen.tc.tnc.message.util.DefaultByteBuffer;
import de.hsbremen.tc.tnc.report.ImvRecommendationPair;
import de.hsbremen.tc.tnc.report.enums.ImHandshakeRetryReasonEnum;

class ImvConnectionAdapterIetf implements ImvConnectionAdapter {
	
	// TODO these seems to be better somewhere else, ideas?
	private static final byte VERSION = 1;
	
	private ImWriter<ImMessage> byteWriter;
	private IMVConnection connection;
	
	
	@SuppressWarnings("unchecked")
	ImvConnectionAdapterIetf(ImWriter<? extends ImMessage> byteWriter,
			IMVConnection connection) {
		this.byteWriter = (ImWriter<ImMessage>) byteWriter;
		this.connection = connection;
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.im.adapter.connection.ImcConnectionAdapter#sendMessage(de.hsbremen.tc.tnc.im.adapter.ImComponent, long, short)
	 */
	@Override
	public void sendMessage( ImObjectComponent component, long identifier)
			throws TncException, ValidationException {
		
		if(component != null && component.getAttributes() != null){
			ImMessage message = PaMessageFactoryIetf.createMessage(VERSION, identifier, this.filterTypes(component.getAttributes()));
			
			byte [] byteMessage = this.messageToByteArray(message);
			
			byte flags = 0;
			for (ImComponentFlagsEnum flagEnum: component.getImFlags()) {
				flags |= flagEnum.bit();
			}
					
			try {
				this.send(flags,component.getVendorId(), component.getType(), component.getCollectorId(), component.getValidatorId(), byteMessage);
			} catch (TNCException e) {
				throw new TncException(e);
			}

		} // else ignore and do nothing	
		
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.im.adapter.connection.ImcConnectionAdapter#requestHandshakeRetry(long)
	 */
	@Override
	public void requestHandshakeRetry(ImHandshakeRetryReasonEnum reason) throws TncException {
		if(reason.toString().contains("IMV")){
			try {
				this.connection.requestHandshakeRetry(reason.id());
			} catch (TNCException e) {
				throw new TncException(e);
			}
		}else{
			throw new TncException("Reason is not useable with IMV and IMVConnection.", TncExceptionCodeEnum.TNC_RESULT_INVALID_PARAMETER);
		}
	}

	@Override
	public void provideRecommendation(ImvRecommendationPair pair) throws TncException {
		
		try {
			this.connection.provideRecommendation(pair.getRecommendationId(), pair.getResultId());
		} catch (TNCException e) {
			throw new TncException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.im.adapter.connection.ImcConnectionAdapter#getAttribute(de.hsbremen.tc.tnc.attribute.TncAttributeType)
	 */
	@Override
	public Object getAttribute(TncAttributeType type) throws TncException {
		try {
			return  this.connection.getAttribute(type.id());
		} catch (TNCException e) {
			throw new TncException(e);
		} catch (UnsupportedOperationException e1){
			throw new UnsupportedOperationException("The connection " + this.connection.toString() + " of type " 
				+ this.connection.getClass().getCanonicalName() + " does not support attributes.", e1);
		}
	}

	private void send(byte flags, long vendorId, long type, long collectorId, long validatorId, byte[] message) throws TNCException{
		
		// FIXME it maybe better to check the IMV type here too.
		if(this.connection instanceof IMVConnectionLong && validatorId != HSBConstants.HSB_IM_ID_UNKNOWN){
			((IMVConnectionLong) this.connection).sendMessageLong(flags, vendorId, type, message, collectorId, validatorId);
		}else{
			if(type >= TNCConstants.TNC_SUBTYPE_ANY || vendorId == TNCConstants.TNC_VENDORID_ANY){
				throw new TNCException("Connection does not support the message type "+ type +" with vendor ID " + vendorId 
						+ ", type cannot be greater than " + TNCConstants.TNC_SUBTYPE_ANY + " and vendor ID cannot be greater than "
						+ TNCConstants.TNC_VENDORID_ANY+".", TNCException.TNC_RESULT_NO_LONG_MESSAGE_TYPES);
			}
			long msgType = (long)(vendorId << 8) | (type & 0xFF);
			this.connection.sendMessage(msgType, message);
		}
		
	}
	
	private List<PaAttribute> filterTypes(List<? extends ImAttribute> imAttributes){
		List<PaAttribute> attributes = new LinkedList<>();
		for (ImAttribute attr : imAttributes) {
			if(attr instanceof PaAttribute){
				attributes.add((PaAttribute)attr);
			}
		}
		return attributes;
	}
	
	private byte[] messageToByteArray(ImMessage message) throws TncException{
		
		ByteBuffer buf = new DefaultByteBuffer(message.getHeader().getLength());
		try {
			this.byteWriter.write(message, buf);
		} catch (SerializationException e) {
			throw new TncException(e.getMessage(), TncExceptionCodeEnum.TNC_RESULT_OTHER);
		}
	
		return buf.read((int)(buf.bytesWritten()-buf.bytesRead()));
	
	}

}
