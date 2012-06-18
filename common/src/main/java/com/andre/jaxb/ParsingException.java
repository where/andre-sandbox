package com.andre.jaxb;

import java.util.*;

/**
 * Base parsing exception.
 */
public class ParsingException extends Exception {
	public ParsingException() {
	}

	public ParsingException(String msg)
	{
		super(msg);
	}

	public ParsingException(Throwable throwable) {
		super(throwable);
	}

	public ParsingException(String message, Throwable throwable) {
		super(message,throwable);
	}
}
