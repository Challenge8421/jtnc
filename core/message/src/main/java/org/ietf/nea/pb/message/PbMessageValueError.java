package org.ietf.nea.pb.message;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import org.ietf.nea.pb.message.enums.PbMessageErrorFlagsEnum;
import org.ietf.nea.pb.message.util.AbstractPbMessageValueErrorParameter;

/**
 * Reference IETF RFC 5793 section 4.9:
 * ------------------------------------
 * The PB-TNC message type named PB-Error (value 5) is used by the
   Posture Broker Client or Posture Broker Server to indicate that an
   error has occurred.  The Posture Broker Client or Posture Broker
   Server MAY include one or more messages of this type in any batch of
   any type.  Other messages may also be included in the same batch.
   The party that receives a PB-Error message MAY log it or take other
   action as deemed appropriate.  If the FATAL flag is set (value 1),
   the recipient MUST terminate the PB-TNC session after processing the
   batch without sending any messages in response.  Every Posture Broker
   Client and Posture Broker Server MUST implement this message type.
   
   The NOSKIP flag in the PB-TNC Message Header MUST be set for this
   message type.
   
   Since the Error Parameters field is variable length, the value in 
   the PB-TNC Message Length field will vary also. However, it MUST 
   always be at least 20 to cover the fixed-length fields.
 */
public class PbMessageValueError extends AbstractPbMessageValue {

    private static final boolean OMMITTABLE = Boolean.FALSE;
    
    private final EnumSet<PbMessageErrorFlagsEnum> errorFlags; //  8 bit(s) 
    private final long errorVendorId;                           // 24 bit(s)
    private final int errorCode;                              // 16 bit(s)
    private AbstractPbMessageValueErrorParameter errorParameter; //32 bit(s)
    
    

	PbMessageValueError(final PbMessageErrorFlagsEnum[] flags, final long errorVendorId,
			final int errorCode, final long length,
			final AbstractPbMessageValueErrorParameter errorParameter) {
		super(length, OMMITTABLE);
		
		if(flags != null && flags.length > 0){
	       	this.errorFlags = EnumSet.copyOf(Arrays.asList(flags));
	    }else{
	     	this.errorFlags = EnumSet.noneOf(PbMessageErrorFlagsEnum.class);
	    }

		this.errorVendorId = errorVendorId;
		this.errorCode = errorCode;
		this.errorParameter = errorParameter;
	}

	/**
	 * @return the errorFlags
	 */
	public Set<PbMessageErrorFlagsEnum> getErrorFlags() {
		return Collections.unmodifiableSet(this.errorFlags);
	}

	/**
	 * @return the errVendorId
	 */
	public long getErrorVendorId() {
		return this.errorVendorId;
	}


	/**
	 * @return the errCode
	 */
	public int getErrorCode() {
		return this.errorCode;
	}


	/**
	 * @return the content
	 */
	public AbstractPbMessageValueErrorParameter getErrorParameter() {

		return this.errorParameter;
	}

}