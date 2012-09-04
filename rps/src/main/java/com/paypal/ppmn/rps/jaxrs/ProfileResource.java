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

@Path("/profile/")
public class ProfileResource extends BaseResource {
	private static final Logger logger = Logger.getLogger(ProfileResource.class);
	private ProfileService profileService ;

	public ProfileResource(ProfileService profileService) {
		this.profileService = profileService ;
	}

	@GET
	@Path("/{id}/")
	@Produces({ MediaType.APPLICATION_JSON, CONTENT_PROTOBUF })
	public Profile getProfile(@PathParam("id") String id) throws Exception { 
		Profile obj = profileService.get(id);
		logger.debug("id="+id+" obj="+obj);

		if (obj == null) 
			throw new WebApplicationException(Response.Status.NOT_FOUND); 
		return obj;
	}

	@PUT
	@Path("/{id}/")
	@Consumes({ MediaType.APPLICATION_JSON, CONTENT_PROTOBUF })
	public Response update(@PathParam("id") String id, Profile profile) throws Exception {
		logger.debug("id="+id+" profile="+profile);
		profile.id = id ;
		profileService.save(profile);
		return ResponseUtils.createPut();
	}

	@DELETE
	@Path("/{id}/")
	public Response delete(@PathParam("id") String id) throws Exception {
		logger.debug("id="+id);
		profileService.delete(id);
		return ResponseUtils.createDelete();
	}
}
