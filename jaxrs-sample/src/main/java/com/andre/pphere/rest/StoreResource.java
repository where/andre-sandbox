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
import javax.ws.rs.MatrixParam;
import javax.ws.rs.DefaultValue;

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

import com.andre.pphere.data.Store;
import com.andre.pphere.data.StoreList;
import com.andre.pphere.dao.StoreDao;
import com.andre.rest.ResponseUtils;
import com.andre.rest.data.Link;

@Path("/store/")
public class StoreResource {
	 private static final Logger logger = Logger.getLogger(OfferResource.class);
	private final static Double NULL = null;
	private StoreDao dao ;
	private @Context UriInfo uriInfo;

	public StoreResource(StoreDao dao) {
		this.dao = dao ;
	}

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Get Stores", target = DocTarget.METHOD),
		})
	public StoreList getStores(
				@QueryParam("lat") Double lat,
				@QueryParam("lng") Double lng,
				@QueryParam("radius") Double radius)
		{
		StoreList list = new StoreList(dao.getStores());
		return list;
	}

	@GET
	@Path("/{id}/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Get Store", target = DocTarget.METHOD),
	})
	public Store getStore(@PathParam("id") Integer id, 
			@Context HttpHeaders headers,
			@Context Request request
		) {
		Store obj = dao.getStore(id);
		if (obj == null) 
			throw new WebApplicationException(Response.Status.NOT_FOUND); 
		return obj;
	}

	@POST
	@Path("/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Adds a new Store", target = DocTarget.METHOD),
		@Description(value = "Requested Store", target = DocTarget.RETURN),
		})
	public Response create(Store obj, @Context HttpHeaders headers) {
		try {
			int id = dao.createStore(obj);
			obj.setId(id);
			return ResponseUtils.createPost(""+uriInfo.getAbsolutePath(), ""+id,obj);
		} catch (Exception e) {
		 	return ResponseUtils.createServerError(""+e) ;
		}
	}

	@PUT
	@Path("/{id}/")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Updates store", target = DocTarget.METHOD)
	})
	public Response update(@PathParam("id") Integer id, Store obj) {
		obj.setId(id);
		dao.updateStore(obj);
		return ResponseUtils.createPut();
	}

	@DELETE
	@Path("/{id}/")
	@Descriptions({ 
		@Description(value = "Deletes a Store", target = DocTarget.METHOD),
	})
	public Response delete(@PathParam("id") Integer id) {
		dao.deleteStore(id);
		return ResponseUtils.createDelete();
	}

	@POST
	@Path("/{id}/checkin")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Performs store check-in process. Makes store searchable", target = DocTarget.METHOD)
	})
	public void checkin(Store obj, @Context HttpHeaders headers) {
		logger.debug("checkin");
	}

	@POST
	@Path("/{id}/checkout")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Descriptions({ 
		@Description(value = "Performs store check-out process.", target = DocTarget.METHOD)
	})
	public void checkout(Store obj, @Context HttpHeaders headers) {
		logger.debug("checkout");
	}
}
