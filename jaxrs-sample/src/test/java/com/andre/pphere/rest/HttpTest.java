package com.andre.pphere.rest;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.http.RestHttpClient;
import com.andre.pphere.data.Store;
import com.andre.pphere.data.StoreList;

public class HttpTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(HttpTest.class);
	private long key = makeUniqueKey();
	private String lastPostUrl  ;
	//public static final String JAVA_SERIALIZED = "application/x-java-serialized-object" ;

	@DataProvider(name = "getProvider") 
	public Object[][] getProvider() {
		return new Object[][] {
			{ "application/json"}
			,{ "application/xml"}
			//,{ JAVA_SERIALIZED }
		};
	}

	@DataProvider(name = "postProvider") 
	public Object[][] postProvider() {
		return new Object[][] {
			{ "application/json", contentFactory.getStore("json") }
			,{ "application/xml", contentFactory.getStore("xml") }
			//,{ JAVA_SERIALIZED, contentFactory.getStore(JAVA_SERIALIZED) }
		};
	}

	@Test(dataProvider = "getProvider")
	public void getStores(String contentType) throws Exception {
		String url = storeUrl;
		logger.debug("stores: url="+url);
		RestHttpClient.Result result = httpClient.get(url,makeHeaders(contentType));
		assertStatus(result.statusCode);
		Assert.assertNotNull(result.response);
		String svalue = new String(result.response);
		logger.debug("stores: "+svalue);
	}

	@Test
	public void getNonExistentStore() throws Exception {
		String url = makeKeyUrl(key);
		RestHttpClient.Result result = httpClient.get(url);
		Assert.assertEquals(result.statusCode,404);
	}
	@Test
	public void deleteNonExistentStore() throws Exception {
		String url = makeKeyUrl(key);
		RestHttpClient.Result result = httpClient.delete(url);
		Assert.assertEquals(result.statusCode,404);
	}

	@Test(dataProvider = "postProvider")
	public void testPostGet(String contentType, byte [] content) throws Exception {

		// Post the data
		logger.debug("contentType="+contentType);
		//logger.debug("content="+new String(content));
		String url = storeUrl;
		RestHttpClient.Result result = httpClient.post(url,content,makeHeaders(contentType));
		assertStatus(result);

		// Get URL of new resource from Location header
		String locationUrl = result.getHeaderValue("Location");
		Assert.assertNotNull(locationUrl);
		lastPostUrl = locationUrl;

		// Ger the new URL
		result = httpClient.get(locationUrl,makeHeaders(contentType));
		assertStatus(result);
		Assert.assertNotNull(result.response);

		// TODO: Should compare POST request and GET response bodies
		String getResponse = new String(result.response); 
		Object obj = readObject(contentType, getResponse); // NOTE: Works for Jackson JSON but not for default JSON provider
		Store store = (Store)obj;
	}

	@Test(dataProvider = "postProvider", dependsOnMethods="testPostGet")
	public void testPutGet(String contentType, byte [] content) throws Exception {
		RestHttpClient.Result result ;
		String url = lastPostUrl;

		result = httpClient.get(url);
		assertStatus(result);
		Assert.assertNotNull(result.response);
		String response1 = new String(result.response);
		//logger.debug("  == xml="+response1);

		result = httpClient.put(url,content,makeHeaders(contentType));
		assertStatus(result);

		result = httpClient.get(url);
		assertStatus(result);
		Assert.assertNotNull(result.response);
		String response2 = new String(result.response);

		//Assert.assertEquals(response2,response1); // TODO: convert to JSON/XML and compare canonically
	}

	@Test(dependsOnMethods="testPutGet")
	public void testDeleteGet() throws Exception {
		RestHttpClient.Result result ;
		String url = lastPostUrl;

		result = httpClient.delete(url);
		assertStatus(result);

		result = httpClient.get(url);
		Assert.assertEquals(result.statusCode,404);
	}
}
