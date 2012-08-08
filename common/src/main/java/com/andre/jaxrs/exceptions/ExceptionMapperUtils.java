package com.andre.jaxrs.exceptions;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;

import com.andre.jaxrs.ApplicationErrors;
import com.andre.jaxrs.Headers;
import com.andre.jaxrs.data.Error; 
import com.andre.jaxb.InvalidFormatException;
import org.codehaus.jackson.map.JsonMappingException;

public class ExceptionMapperUtils {
	private static final Logger logger = Logger.getLogger(ExceptionMapperUtils.class);

	static public Response toResponseInvalidFormat(Exception ex) {
		logger.debug("-------------------");
		logger.debug("Exception: "+ex);
		logger.debug("Exception.cause: "+ex.getCause());

		String appError = ApplicationErrors.VALIDATION_FAILED ;
		int status = 400 ; 
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
