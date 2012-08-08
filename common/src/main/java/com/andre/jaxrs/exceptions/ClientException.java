package com.andre.jaxrs.exceptions;

import java.util.*;

/**
 * ClientException exception.
 */
public class ClientException extends Exception {
	public ClientException() {
	}

	public ClientException(String msg) {
		super(msg);
	}

	public ClientException(String msg, String appErrorCode) {
		super(msg);
		this.appErrorCode = appErrorCode ;
	}

	public ClientException(Throwable throwable) {
		super(throwable);
	}

	public ClientException(String message, Throwable throwable) {
		super(message,throwable);
	}

 
	private String appErrorCode; 
	public String getAppErrorCode() { return appErrorCode; }
	public void setAppErrorCode(String appErrorCode) { this.appErrorCode = appErrorCode; } 
}
