package com.andre.jaxrs.exceptions;

import java.util.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.andre.jaxb.InvalidFormatException;

/** 
 * Customize exception handling - Validation failured.  
 */
@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {
	public Response toResponse(InvalidFormatException ex) {
		return ExceptionMapperUtils.toResponseInvalidFormat(ex);
	}
}
