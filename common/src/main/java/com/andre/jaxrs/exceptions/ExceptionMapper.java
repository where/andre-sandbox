package com.andre.jaxrs.exceptions;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;
import com.andre.jaxb.InvalidFormatException;

import com.andre.jaxrs.ApplicationErrors;
import com.andre.jaxrs.Headers;
import com.andre.jaxrs.data.Error; 

/** 
 */
@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {
	private static final Logger logger = Logger.getLogger(ExceptionMapper.class);

	public Response toResponse(Exception ex) {
		logger.debug("-------------------");
		logger.debug("Exception: "+ex);
		Throwable cause = ex.getCause();
		logger.debug("Exception.cause: "+cause);
		if (cause != null) {
			logger.debug("Exception.cause.class: "+cause.getClass().getName());
			if (cause instanceof InvalidFormatException)
        		return ExceptionMapperUtils.toResponseInvalidFormat((InvalidFormatException)cause);
		}
		ex.printStackTrace();

		String appError = ApplicationErrors.GENERIC_SERVER_ERROR ;
		int status = 500 ; 
		String emsg = ex.getMessage();
		logger.debug("status="+status+" appError="+appError+" msg="+emsg);

		Error error = new Error(status, emsg, appError);
		Response.ResponseBuilder rb = Response.status(status);
		if (appError != null) {
			rb.header(Headers.APP_ERROR_CODE,appError);
			rb.header(Headers.APP_ERROR_MESSAGE,emsg);
			rb.entity(error);
		}
		return rb.build();
	}

}
