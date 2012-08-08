package com.andre.jaxrs.exceptions;

import com.andre.jaxrs.ApplicationErrors;
import com.andre.jaxrs.ResponseUtils;
import java.util.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/** 
 * Customize exception handling - Validation failured.  
 */
@Provider
public class ClientExceptionMapper implements ExceptionMapper<ClientException> {
	public Response toResponse(ClientException ex) {
		return ResponseUtils.createClientError(ex.getAppErrorCode()==null ?  ApplicationErrors.GENERIC_CLIENT_ERROR : ex.getAppErrorCode(), ex.getMessage());
	}
}
