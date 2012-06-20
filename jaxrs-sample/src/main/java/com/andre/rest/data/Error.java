package com.andre.rest.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Error")
@XmlType(name = "Error", propOrder = {
	"httpStatusCode",
	"applicationCode",
	"message"
})
public class Error implements java.io.Serializable {
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
	@XmlElement(required=true)
	public int getHttpStatusCode() { return httpStatusCode; }
	public void setHttpStatusCode(int val) { httpStatusCode=val; } 

	private String applicationCode;
	@XmlElement(required=false)
	public String getApplicationCode() { return applicationCode; }
	public void setApplicationCode(String val) { applicationCode=val; } 

	private String message;
	@XmlElement(required=false)
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
