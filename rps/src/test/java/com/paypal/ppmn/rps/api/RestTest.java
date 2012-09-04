package com.paypal.ppmn.rps.api;

import com.paypal.ppmn.rps.BaseTest;
import com.paypal.ppmn.rps.data.Profile;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.http.RestHttpClient;
import com.andre.util.TestNgUtils;

public class RestTest extends BaseRestTest {
	private static Logger logger = Logger.getLogger(RestTest.class);
	private String key = "rest-key" ;

	@Test
	public void put() throws Exception {
		String url = makeUrl(key);
		//logger.debug("profileUrl="+url);
		Profile profile = newProfile(key, value) ;
		//logger.debug("profile="+profile);

		byte [] content = objectMapper.toBytes(profile);
		RestHttpClient.Result result = httpClient.put(url,content);
		assertStatus(result.statusCode);
	}

	@Test (dependsOnMethods="put")
	public void get() throws Exception {
		String url = makeUrl(key);

		RestHttpClient.Result result = httpClient.get(url);
		assertStatus(result.statusCode);
		Assert.assertNotNull(result.response);

        Profile profile = objectMapper.toObject(result.response,Profile.class);
		Assert.assertEquals(profile.id,key);

		//String svalue = new String(result.response);
		//logger.debug("profile: "+svalue);
	}

	@Test (dependsOnMethods="get")
	public void delete() throws Exception {
		String url = makeUrl(key);
		RestHttpClient.Result result = httpClient.delete(url);
		assertStatus(result.statusCode);

		result = httpClient.get(url);
		Assert.assertEquals(result.statusCode,404);
	}
}
