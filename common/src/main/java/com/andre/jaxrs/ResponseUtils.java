package com.andre.jaxrs;

import com.andre.jaxrs.data.Error;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.WebApplicationException;
import java.util.*;

/**
 * REST Response utilities.
 * @author amesarovic
 */
public class ResponseUtils {
	public static final String HEADER_APP_ERROR_CODE = "X-app-errror" ;
	public static final String HEADER_APP_ERROR_MESSAGE = "X-app-errror-message" ;
	public static final String HEADER_LOCATION = "Location" ;

	public static Response createGet(Object entity) {
		Response.ResponseBuilder rb = Response.status(Response.Status.OK);
		rb.entity(entity);
		Response r = rb.build();
		return r;
	}

	public static Response createPost(String resourceUrl, int id) {
		return createPost(resourceUrl, ""+id) ;
	}

	public static Response createPost(String resourceUrl, String id) {
		Response.ResponseBuilder rb = Response.status(Response.Status.CREATED);
		rb.header("Location", resourceUrl + "/" + id);
		Response r = rb.build();
		return r;
	}

	public static Response createPost(String resourceUrl, String id, Object entity) {
		Response.ResponseBuilder rb = Response.status(Response.Status.CREATED);
		rb.entity(entity);
		rb.header("Location", resourceUrl + "/" + id);
		Response r = rb.build();
		return r;
	}

	public static Response createPut() {
		Response r = Response.ok().build();
		return r;
	}

	public static Response createPut(Object entity) {
		Response.ResponseBuilder rb = Response.status(Response.Status.OK);
		rb.entity(entity);
		Response r = rb.build();
		return r;
	}

	public static Response createDelete() {
		Response r = Response.ok().build();
		return r;
	}

	public static Response createClientError(String msg) {
		return createClientError(null, msg, Response.Status.BAD_REQUEST);
	}
	public static Response createClientError(String applicationCode, String msg) {
		return createClientError(applicationCode, msg, Response.Status.BAD_REQUEST);
	}
	public static Response createClientError(String applicationCode, String msg, Response.Status status) {
		Response.ResponseBuilder rb = Response.status(status);

		Error error = new Error(Response.Status.BAD_REQUEST.getStatusCode(),msg);
		if (null != applicationCode) {
			error.setApplicationCode(applicationCode);
			rb.header(HEADER_APP_ERROR_CODE, applicationCode);
		}
		rb.entity(error);
		rb.header(HEADER_APP_ERROR_MESSAGE, msg);
		Response r = rb.build();
		return r ;
	}

	public static Response createServerError(String msg) {
		Response.ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		rb.header(HEADER_APP_ERROR_MESSAGE, msg);

		Error error = new Error(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),msg);
		rb.entity(error);

		Response r = rb.build();
		return r ;
	}

	public static Response createError(Response.Status status, String msg) {
		Response.ResponseBuilder rb = Response.status(status);
		//rb.header(HEADER_APP_ERROR, msg);
		Response r = rb.build();
		return r ;
	}

	static public String getHeader(Response response, String headerName) {
		MultivaluedMap<String,Object> map = response.getMetadata();
		if (map == null) return null ;

		Object val = map.get(headerName);
		if (!(val instanceof ArrayList)) return null;
		ArrayList list = (ArrayList)val;
		if (list.size() == 0) return null ;
		Object obj = list.get(0);
		if (!(obj instanceof String)) return null;
		String headerValue = (String)obj ;
		return headerValue ;
	}

	static public String getCreatedId(Response response) {
		String url = getHeader(response, HEADER_LOCATION);
		if (url == null) return null ;
		int idx = url.lastIndexOf("/");
		if (idx == -1) return null ;
		String id = url.substring(idx+1, url.length());
		return id ;
	}
}
