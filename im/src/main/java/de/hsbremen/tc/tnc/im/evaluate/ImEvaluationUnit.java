package de.hsbremen.tc.tnc.im.evaluate;

import de.hsbremen.tc.tnc.m.attribute.ImAttribute;

interface ImEvaluationUnit extends ImEvaluationComponent<ImAttribute> {
	
	long getVendorId();
	
	long getType(); 
}
