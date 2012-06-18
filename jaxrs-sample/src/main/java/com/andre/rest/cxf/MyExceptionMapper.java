package com.andre.rest.cxf;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.xml.bind.JAXBException;
import javax.ws.rs.ext.Provider;
import java.io.*;
import org.apache.log4j.Logger;

/** Expiremental to test custom error */
@Provider
public class MyExceptionMapper implements ExceptionMapper<IOException> {
	private static final Logger logger = Logger.getLogger(MyExceptionMapper.class);

	public Response toResponse(IOException ex) {
		logger.debug("Exception: "+ex);
		return Response.status(Response.Status.BAD_REQUEST).build(); // 400
		//return Response.status(Response.Status.PRECONDITION_FAILED).build(); // 412
		//return 422  Unprocessable Entity
	}
}
