package com.andre.jaxrs.exceptions;

import com.andre.jaxrs.ApplicationErrors;
import com.andre.jaxrs.data.Error;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.xml.bind.JAXBException;
import javax.ws.rs.ext.Provider;
import java.io.*;
import org.apache.log4j.Logger;
import javax.ws.rs.WebApplicationException;

/** 
 * Customize exception handling - Illegal syntax and Validation failured.  
 */
@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {
	private static final Logger logger = Logger.getLogger(DefaultExceptionMapper.class);
	//private static final String SAXParseException = "org.xml.sax.SAXParseException" ;
	//private static final String WebApplicationException = "javax.ws.rs.WebApplicationException";
	//private static final String WstxUnexpectedCharException = "com.ctc.wstx.exc.WstxUnexpectedCharException" ;

	public Response toResponse(Exception ex) {
		logger.debug("-------------------");
		logger.debug("Exception: "+ex);
		logger.debug("Exception.cause: "+ex.getCause());

 		//Response response = ex.getResponse() ;
		//int status = response.getStatus();
		//logger.debug("status.1="+status);

		String appError = ApplicationErrors.UNKNOWN;
/*
		Throwable cause = ex.getCause();
		if (cause != null ) {
			String cname = cause.getClass().getName() ;
			status = Response.Status.BAD_REQUEST.getStatusCode();
			if (SAXParseException.equals(cname)) {
				appError = ApplicationErrors.VALIDATION_FAILED ;
			} else if (WstxUnexpectedCharException.equals(cname)) {
				appError = ApplicationErrors.ILLEGAL_SYNTAX ;
			}
		}
		logger.debug("status.2="+status);
*/
		String emsg = ex.getMessage();
		Error error = new Error(500, emsg, appError);
		Response.ResponseBuilder rb = Response.status(500);
		rb.entity(error);
		return rb.build();
	}
}

/*
JSON Bad Syntax:
	exception: org.codehaus.jackson.JsonParseException
	cause: null

JSON Invalid
	No validation!

XML Bad Syntax:
	exception: javax.ws.rs.WebApplicationException
	cause: com.ctc.wstx.exc.WstxUnexpectedCharException

XML Invalid
	exception: javax.ws.rs.WebApplicationException
	cause: org.xml.sax.SAXParseException

StatusCodes:
	400 Response.Status.BAD_REQUEST)
	412 Response.Status.PRECONDITION_FAILED)
	422  Unprocessable Entity
*/
