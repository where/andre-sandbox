package com.paypal.ppmn.rps.jaxrs;

import java.util.*;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.WebApplicationException;

import com.andre.jaxrs.ResponseUtils;
import com.andre.jaxrs.RestUtils;
import com.paypal.ppmn.rps.service.ProfileService;
import com.paypal.ppmn.rps.data.Profile;

import org.apache.log4j.Logger;

@Path("/ping/")
public class PingResource {
	private static final Logger logger = Logger.getLogger(PingResource.class);

	@GET
	@Path("/")
	//@Produces({ MediaType.APPLICATION_JSON, CONTENT_PROTOBUF })
	public void getProfile() { 
		logger.debug("ping");
	}
}
