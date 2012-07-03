package com.andre.jaxb;

import java.util.*;
import com.andre.util.CollectionUtils;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 * JAXB custom validation event handler.
 */
public class CustomValidationEventHandler implements ValidationEventHandler {

	private List<String> errors = new ArrayList<String>();

	public List<String> getErrors() { 
		return errors ; 
	}

	// allow unmarshalling to continue even if there are errors
	public boolean handleEvent(ValidationEvent vevent) {
		// ignore warnings
		if (vevent.getSeverity() != ValidationEvent.WARNING) {
			ValidationEventLocator veventLoc = vevent.getLocator();
			String emsg =
				"Line:Col[" + veventLoc.getLineNumber() +
				":" + veventLoc.getColumnNumber() +
				"]:" + vevent.getMessage() ;
			errors.add(emsg);
		}
		return true;
	}

	@Override 
	public String toString() {
		return CollectionUtils.toString(errors);
	}
}
