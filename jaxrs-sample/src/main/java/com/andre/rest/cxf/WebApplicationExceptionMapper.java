package com.andre.rest.cxf;

import com.andre.rest.ApplicationErrors;
import com.andre.rest.data.Error; 
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.xml.bind.JAXBException;
import javax.ws.rs.ext.Provider;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.Produces;
import org.xml.sax.SAXParseException;
import com.ctc.wstx.exc.WstxUnexpectedCharException;

/** 
 * Customize exception handling - Illegal syntax and Validation failured.  
 */
@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {
	private static final Logger logger = Logger.getLogger(WebApplicationExceptionMapper.class);
	private static final String SAXParseException = "org.xml.sax.SAXParseException" ;
	private static final String WstxUnexpectedCharException = "com.ctc.wstx.exc.WstxUnexpectedCharException" ;

	public Response toResponse(WebApplicationException ex) {
		logger.debug("-------------------");
		logger.debug("Exception: "+ex);
		logger.debug("Exception.cause: "+ex.getCause());

 		Response response = ex.getResponse() ;
		dump(response.getMetadata());
		logger.debug("response="+response);
		logger.debug("response.entity="+response.getEntity());

		int status = response.getStatus();
		logger.debug("status.1="+status);

		String appError = null;
		Throwable cause = ex.getCause();
		if (status == 400 && cause != null ) {
			if (cause instanceof SAXParseException) {
				appError = ApplicationErrors.VALIDATION_FAILED ;
			} else if (cause instanceof WstxUnexpectedCharException) {
				appError = ApplicationErrors.ILLEGAL_SYNTAX ;
			}
		}
		String emsg = ex.getMessage();
		logger.debug("status.2="+status+" appError="+appError+" msg="+emsg);
		Error error = new Error(status, emsg, appError);
		Response.ResponseBuilder rb = Response.status(status);
		if (appError != null) 
			rb.entity(error);
		//rb.type(MediaType.APPLICATION_XML); // HACK: http://www.mail-archive.com/users@cxf.apache.org/msg08310.html
		return rb.build();
	}

	private void dump(MultivaluedMap<String,Object> map) {
		System.out.println(">> MAP: size="+map.size());
		for (String key : map.keySet() ) {
			System.out.println(">>   key="+key+" val="+map.get(key));
		}
	}
}
