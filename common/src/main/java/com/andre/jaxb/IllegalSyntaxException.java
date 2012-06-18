package com.andre.jaxb;

import java.util.*;

/**
 * Thrown if the content is not well-formed.
 */
public class IllegalSyntaxException extends ParsingException {
	public IllegalSyntaxException() {
	}

	public IllegalSyntaxException(String msg)
	{
		super(msg);
	}

	public IllegalSyntaxException(Throwable throwable) {
		super(throwable);
	}

	public IllegalSyntaxException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
