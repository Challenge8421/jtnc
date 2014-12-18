package org.ietf.nea.pt;

import de.hsbremen.tc.tnc.HSBConstants;
import de.hsbremen.tc.tnc.attribute.TncAttributeType;
import de.hsbremen.tc.tnc.attribute.TncCommonAttributeTypeEnum;
import de.hsbremen.tc.tnc.exception.TncException;
import de.hsbremen.tc.tnc.exception.enums.TncExceptionCodeEnum;
import de.hsbremen.tc.tnc.transport.TransportAttributes;

public class DefaultTransportAttributes implements TransportAttributes{

	private final String tVersion;
	private final String tProtocol;
	private final long maxMessageLength;
	private final long maxMessageLengthPerIm;
	private final long maxRoundTrips;
	
	public DefaultTransportAttributes(String tProtocol, String tVersion){
		this(tProtocol, tVersion, HSBConstants.TCG_IM_MAX_MESSAGE_SIZE_UNKNOWN, HSBConstants.HSB_TRSPT_MAX_MESSAGE_SIZE_UNKNOWN, HSBConstants.TCG_IM_MAX_ROUND_TRIPS_UNKNOWN);
	}
	
	public DefaultTransportAttributes(String tProtocol, String tVersion, 
			long maxMessageLength, long maxMessageLengthPerIm, long maxRoundTrips) {
		this.tProtocol = tProtocol;
		this.tVersion = tVersion;
		this.maxMessageLength = maxMessageLength;
		this.maxMessageLengthPerIm = maxMessageLengthPerIm;
		this.maxRoundTrips = maxRoundTrips;
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.transport.TransportAttributes#getTransportVersion()
	 */
	@Override
	public String getTransportVersion() {
		return this.tVersion;
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.transport.TransportAttributes#getTransportProtocol()
	 */
	@Override
	public String getTransportProtocol() {
		return this.tProtocol;
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.transport.TransportAttributes#getMaxMessageLength()
	 */
	@Override
	public long getMaxMessageLength() {
		return this.maxMessageLength;
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.transport.TransportAttributes#getMaxMessageLengthPerIm()
	 */
	@Override
	public long getMaxMessageLengthPerIm() {
		return this.maxMessageLengthPerIm;
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.transport.TransportAttributes#getMaxRoundTrips()
	 */
	@Override
	public long getMaxRoundTrips() {
		return this.maxRoundTrips;
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.transport.TransportAttributes#getAttribute(de.hsbremen.tc.tnc.attribute.TncAttributeType)
	 */
	@Override
	public Object getAttribute(TncAttributeType type) throws TncException {
		
		if(type.equals(TncCommonAttributeTypeEnum.TNC_ATTRIBUTEID_IFT_PROTOCOL)){
			return this.tProtocol;
		}
		
		if(type.equals(TncCommonAttributeTypeEnum.TNC_ATTRIBUTEID_IFT_VERSION)){
			return this.tVersion;
		}
		
		if(type.equals(TncCommonAttributeTypeEnum.TNC_ATTRIBUTEID_MAX_MESSAGE_SIZE)){
			return this.maxMessageLengthPerIm;
		}
		
		if(type.equals(TncCommonAttributeTypeEnum.TNC_ATTRIBUTEID_MAX_ROUND_TRIPS)){
			return this.maxRoundTrips;
		}
		
		throw new TncException("The attribute with ID " + type.id() + " is unknown.", TncExceptionCodeEnum.TNC_RESULT_INVALID_PARAMETER);
	}

	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.transport.TransportAttributes#setAttribute(de.hsbremen.tc.tnc.attribute.TncAttributeType, java.lang.Object)
	 */
	@Override
	public void setAttribute(TncAttributeType type, Object value)
			throws TncException {
		throw new UnsupportedOperationException("The operation setAttribute(...) is not supported, because there are no attributes to set.");	
	}

}