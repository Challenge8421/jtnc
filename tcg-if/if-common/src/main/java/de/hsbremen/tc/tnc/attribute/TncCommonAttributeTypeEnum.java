package de.hsbremen.tc.tnc.attribute;


public enum TncCommonAttributeTypeEnum implements TncAttributeType {

	/**
    * Preferred human-readable language for a TNCS (type String, 
	* may get from a IM(C/V)Connection) 
    */
    TNC_ATTRIBUTEID_PREFERRED_LANGUAGE (1),
    
    /**
     * Maximum round trips supported by the underlying protocol 
	 * (type Integer, may get from an IM(C/V)Connection)
     */
    TNC_ATTRIBUTEID_MAX_ROUND_TRIPS (0x00559700),

    /**
     * Maximum message size supported by the underlying protocol 
	 * (type Integer, may get from an IM(C/V)Connection)
     */
    TNC_ATTRIBUTEID_MAX_MESSAGE_SIZE (0x00559701),

    /**
     * Diffie-Hellman Pre-Negotiation value provided by the underlying 
	 * protocol (type byte[], may get from an IM(C/V)Connection)
     */
    TNC_ATTRIBUTEID_DHPN_VALUE (0x00559702),

    /**
     * Flag indicating if the connection supports long message types 
	 * (type boolean, may get from an IM(C/V)Connection) 
     */
    // FIXME this is probably never used, because instanceof IM(C/V)ConnectionLong should be used.
 	// Genzel 2014-09-08
    TNC_ATTRIBUTEID_HAS_LONG_TYPES (0x00559703),

    /**
     * Flag indicating if the connection supports exclusive delivery 
	 * of messages (type boolean, may get from an IM(C/V)Connection) 
     */
    TNC_ATTRIBUTEID_HAS_EXCLUSIVE (0x00559704),

    /**
     * Flag indicating if the connection supports SOH functions 
	 * (type boolean, may get from an IM(C/V)Connection) 
     */
    // FIXME this is probably never used, because instanceof IM(C/V)ConnectionSOH should be used.
 	// Genzel 2014-11-08
    TNC_ATTRIBUTEID_HAS_SOH (0x00559705),

    /**
     * IF-TNCCS Protocol Name (type String, may get from 
	 * an IM(C/V)Connection)
     */
    TNC_ATTRIBUTEID_IFTNCCS_PROTOCOL (0x0055970A),

    /**
     * IF-TNCCS Protocol Version (type String, may get from 
	 * an IM(C/V)Connection)
     */
    TNC_ATTRIBUTEID_IFTNCCS_VERSION (0x0055970B),

    /**
     * IF-T Protocol Name (type String, may get from 
	 * an IM(C/V)Connection) 
     */
    TNC_ATTRIBUTEID_IFT_PROTOCOL (0x0055970C),

    /**
     * IF-T Protocol Version (type String, may get from 
	 * an IM(C/V)Connection)
     */
    TNC_ATTRIBUTEID_IFT_VERSION (0x0055970D),

    /**
     * TLS-Unique value provided by the underlying protocol (type 
	 * byte[], may get from a IM(C/V)Connection)
     */
    TNC_ATTRIBUTEID_TLS_UNIQUE (0x0055970E);

	
	private long id;
	
	private TncCommonAttributeTypeEnum(long id){
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see de.hsbremen.tc.tnc.attribute.TncAttributeType#id()
	 */
	@Override
	public long id(){
		return this.id;
	}
}