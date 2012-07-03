package com.andre.jaxb;

import java.util.*;
import com.andre.util.CollectionUtils;

/**
 * Schema validation failure.
 * @author andre
 */
public class InvalidFormatException extends ParsingException {
	private List<String> errors = new ArrayList<String>();
	public List<String> getErrors() { return errors; }
	public void setErrors(List<String> errors) { this.errors = errors; } 

	public InvalidFormatException() {
	}

	public InvalidFormatException(List<String> errors) {
		this.errors = errors ;
	}

	public InvalidFormatException(String msg)
	{
		super(msg);
	}

	public InvalidFormatException(Throwable throwable) {
		super(throwable);
		errors.add(throwable.getMessage());
	}

	public InvalidFormatException(String message, Throwable throwable) {
		super(message,throwable);
	}
 
	@Override
	public String toString() {
		return
				"#errors="+errors.size()
			+ " ERRORS: "+CollectionUtils.toString(errors)
			//+ " msg='"+getMessage()+"'"
			;
	}
}
