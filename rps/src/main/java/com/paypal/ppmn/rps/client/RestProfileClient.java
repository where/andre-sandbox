package com.paypal.ppmn.rps.client;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.paypal.ppmn.rps.data.Profile;
import com.andre.http.RestHttpClient;
import com.andre.mapper.ObjectMapper;

/**
 * Remote HTTP/REST implementation of Client interface.
 * @author amesarovic
 */
public class RestProfileClient implements ProfileClient {
    private static Logger logger = Logger.getLogger(RestProfileClient.class);
	private RestHttpClient httpClient ;
	private String baseUrl ;
	private String profileUrl ;
	private String contentType;
    public ObjectMapper objectMapper ;
	
	public RestProfileClient(RestHttpClient httpClient, String baseUrl, ObjectMapper objectMapper) {
		this.httpClient = httpClient;
		this.baseUrl = baseUrl;
		this.profileUrl = baseUrl + "/profile";
		this.objectMapper = objectMapper;
		this.contentType = objectMapper.getContentType();
	}

	public Profile get(String id) throws Exception {
        String url = makeUrl(id);
        RestHttpClient.Result result = httpClient.get(url);
        if (RestHttpClient.isError(result.statusCode))
			; // TODO
		Profile profile = objectMapper.toObject(result.response,Profile.class);
		logger.debug("id="+id+" profile="+profile);
		return profile;
	}

	public Profile save(Profile profile) throws Exception {
        String url = makeUrl(profile.id);
        RestHttpClient.Result result = httpClient.put(url,objectMapper.toBytes(profile));
        if (RestHttpClient.isError(result.statusCode))
			; // TODO
		return profile ; // TODO - get from response
	}

	public void delete(String id) throws Exception {
        String url = makeUrl(id);
        RestHttpClient.Result result = httpClient.delete(url);
        if (RestHttpClient.isError(result.statusCode))
			; // TODO
	}

	public void ping() throws Exception {
        String url = baseUrl + "/ping";
        RestHttpClient.Result result = httpClient.get(url);
        if (RestHttpClient.isError(result.statusCode))
			; // TODO
	}

    private String makeUrl(String key) {
        return profileUrl + "/" + key;
    }
}
