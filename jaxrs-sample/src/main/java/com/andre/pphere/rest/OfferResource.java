package com.andre.pphere.rest;

import java.util.*;
import org.apache.log4j.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.WebApplicationException;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.andre.pphere.data.Offer;
import com.andre.pphere.data.OfferList;
import com.andre.pphere.dao.OfferDao;
import com.andre.rest.ResponseUtils;
import com.andre.rest.ApplicationErrors;
import com.andre.rest.data.Link;

/**
 * Note: uses CXF-specific WADL annotations.
 */
@Path("/offer/")
public class OfferResource {
	private static final Logger logger = Logger.getLogger(OfferResource.class);	
	private OfferDao dao ;
	private @Context UriInfo uriInfo;

	public OfferResource(OfferDao dao) {
		this.dao = dao ;
	}

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Gets Offers", target = DocTarget.METHOD),
	})
	public Response getOffers(
	 		@QueryParam("lat") Double lat,
	 		@QueryParam("lng") Double lng,
	 		@QueryParam("radius") Double radius) 
		{
		logger.debug("radius="+radius);
		if (lat==null || lng==null || radius==null) // TODO: check for required QP JAX-RS annotation
		 	return ResponseUtils.createClientError(ApplicationErrors.MISSING_QUERY_PARAMETERS,"Missing a required query parameter: lat, lng, or radius");
		else {
			OfferList list = new OfferList(dao.getOffers(lat,lng,radius));
			return ResponseUtils.createGet(list);
		}
	}

	@GET
	@Path("/{id}/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Gets Offer", target = DocTarget.METHOD),
	})
	public Offer getOffer(@PathParam("id") Long id, 
			@Context HttpHeaders headers,
			@Context Request request
		) {
		Offer obj = dao.getOffer(id);
		if (obj == null) 
			throw new WebApplicationException(Response.Status.NOT_FOUND); 
		return obj;
	}

	@POST
	@Path("/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Adds a new Offer", target = DocTarget.METHOD),
		@Description(value = "Requested Offer", target = DocTarget.RETURN),
	})
	public Response create(Offer obj, @Context HttpHeaders headers) {
		long id = dao.createOffer(obj);
		obj.setId(id);
		return ResponseUtils.createPost(""+uriInfo.getAbsolutePath(), ""+id,obj);
	}

	@PUT
	@Path("/{id}/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Updates offer", target = DocTarget.METHOD)
	})
	public Response update(@PathParam("id") Long id, Offer obj) {
		obj.setId(id);
		dao.updateOffer(obj);
		return ResponseUtils.createPut();
	}

	@DELETE
	@Path("/{id}/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Deletes offer", target = DocTarget.METHOD),
	})
	public Response delete(@PathParam("id") Long id) {
		dao.deleteOffer(id);
		return ResponseUtils.createDelete();
	}
}
