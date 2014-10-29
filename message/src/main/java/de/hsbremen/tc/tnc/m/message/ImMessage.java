package de.hsbremen.tc.tnc.m.message;

import java.util.List;

import de.hsbremen.tc.tnc.m.attribute.ImAttribute;

/**
 * Marker for TNCCS message classes.
 * @author Carl-Heinz Genzel
 *
 */
public interface ImMessage {
	
	public ImMessageHeader getHeader();

	public List<? extends ImAttribute> getAttributes();
}