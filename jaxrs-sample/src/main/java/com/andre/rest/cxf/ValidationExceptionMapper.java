package com.andre.rest.cxf;

import com.andre.rest.ApplicationErrors;
import com.andre.rest.data.Error;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.xml.bind.JAXBException;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

/** Not called? */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<JAXBException> {
	private static final Logger logger = Logger.getLogger(ValidationExceptionMapper.class);

	public Response toResponse(JAXBException ex) {
		logger.debug("Exception: "+ex);
		String emsg = ex.getMessage();
		Error error = new Error(Response.Status.BAD_REQUEST.getStatusCode(), emsg, ApplicationErrors.VALIDATION_FAILED);
		Response.ResponseBuilder rb = Response.status(Response.Status.BAD_REQUEST);
		rb.entity(error);
		return rb.build();
	}
}
