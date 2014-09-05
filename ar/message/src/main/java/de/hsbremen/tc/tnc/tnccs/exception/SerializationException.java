package de.hsbremen.tc.tnc.tnccs.exception;

public class SerializationException extends ComprehensibleException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6714101079655195252L;
	
	
	/**
	 * @param arg0
	 */
	public SerializationException(final String message, final String...reasons) {
		super(message, reasons);
	}


	/**
	 * @param message
	 * @param arg1
	 */
	public SerializationException(final String message, final Throwable arg1, final String...reasons) {
		super(message, arg1, reasons);
	}
}
