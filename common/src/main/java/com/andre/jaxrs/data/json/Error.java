package com.andre.jaxrs.data.json;

public class Error {
	public Error() {
	}

	public Error(int httpStatusCode, String message) {
		this.httpStatusCode = httpStatusCode ;
		this.message = message ;
	}

	public Error(int httpStatusCode, String message, String applicationCode) {
		this.httpStatusCode = httpStatusCode ;
		this.message = message ;
		this.applicationCode = applicationCode ;
	}

	private int httpStatusCode;
	public int getHttpStatusCode() { return httpStatusCode; }
	public void setHttpStatusCode(int val) { httpStatusCode=val; } 

	private String applicationCode;
	public String getApplicationCode() { return applicationCode; }
	public void setApplicationCode(String val) { applicationCode=val; } 

	private String message;
	public String getMessage() { return message; }
	public void setMessage(String val) { message=val; } 

	@Override
	public String toString() { 
		return
			  "httpStatusCode="+httpStatusCode
			+ " applicationCode="+applicationCode
			+ " message="+message
			;
	}
}
