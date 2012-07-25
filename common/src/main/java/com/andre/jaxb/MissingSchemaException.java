package com.andre.jaxb;

import java.util.*;

/**
 * Missing schema exception.
 */
public class MissingSchemaException extends Exception {
	public MissingSchemaException() {
	}

	public MissingSchemaException(String msg)
	{
		super(msg);
	}

	public MissingSchemaException(Throwable throwable) {
		super(throwable);
	}

	public MissingSchemaException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
