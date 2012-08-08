package com.andre.jaxrs.exceptions;

import java.util.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.JsonMappingException;

/** 
 * Customize exception handling - Validation failured.  
 */
@Provider
public class JsonMappingExceptionMapper implements ExceptionMapper<JsonMappingException> {
	public Response toResponse(JsonMappingException ex) {
		return ExceptionMapperUtils.toResponseInvalidFormat(ex);
	}
}
