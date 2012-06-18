package com.andre.rest.cxf;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.xml.bind.JAXBException;
import javax.ws.rs.ext.Provider;
import org.apache.log4j.Logger;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<JAXBException> {
	private static final Logger logger = Logger.getLogger(ValidationExceptionMapper.class);

	public Response toResponse(JAXBException ex) {
		logger.debug("Exception: "+ex);
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
}
