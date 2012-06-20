package com.andre.pphere.rest.string;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.http.RestHttpClient;
import com.andre.pphere.data.Store;
import com.andre.pphere.data.StoreList;
import com.andre.pphere.data.*;
import com.andre.pphere.rest.BaseTest;

public class StringTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(StringTest.class);
	private long key = makeUniqueKey();
	private String lastPostUrl  ;
	private static ContentFactory contentFactory = new ContentFactory();

	@DataProvider(name = "postProvider") 
	public Object[][] postProvider() {
		return new Object[][] {
			{ CONTENT_JSON, contentFactory.getStore("json") }
			,{ CONTENT_XML, contentFactory.getStore("xml") }
		};
	}

	@Test(dataProvider = "contentTypes")
	public void getStores(String contentType) throws Exception {
		String url = storeUrl;
		//logger.debug("stores: url="+url);
		RestHttpClient.Result result = httpClient.get(url,makeHeaders(contentType));
		assertStatus(result.statusCode);
		Assert.assertNotNull(result.response);
		String sresponse = new String(result.response);
		//logger.debug("stores: "+sresponse);
		Object obj = readObject(contentType, sresponse, StoreList.class);
		Assert.assertTrue(obj instanceof StoreList);
	}

	@Test
	public void getNonExistentResource() throws Exception {
		String url = makeKeyUrl(key);
		//logger.debug("url="+url);
		RestHttpClient.Result result = httpClient.get(url);
		//logger.debug("result="+result);
		Assert.assertEquals(result.statusCode,404);
	}

	@Test
	public void deleteNonExistentResource() throws Exception {
		String url = makeKeyUrl(key);
		RestHttpClient.Result result = httpClient.delete(url);
		//logger.debug("result="+result);
		Assert.assertEquals(result.statusCode,404);
	}

	@Test(dataProvider = "postProvider")
	public void testPostGet(String contentType, byte [] content) throws Exception {

		// Post the data
		//logger.debug("contentType="+contentType);
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
		Object obj = readObject(contentType, getResponse, Store.class); // NOTE: Works for Jackson JSON but not for default JSON provider
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
