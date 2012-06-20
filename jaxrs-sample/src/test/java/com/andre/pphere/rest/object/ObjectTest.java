package com.andre.pphere.rest.object;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.http.RestHttpClient;
import com.andre.rest.data.Error; // TODO
import com.andre.pphere.data.Store;
import com.andre.pphere.data.StoreList;
import com.andre.pphere.data.*;
import com.andre.rest.ApplicationErrors;
import com.andre.jaxb.JaxbUtils;
import com.andre.pphere.rest.BaseTest;

/**
 * For now, only using XML content - JSON under way.
 * JSON marashalling fails - plain Jackson doesn't match CXF Jackson provider with JAXB.
 */
public class ObjectTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(ObjectTest.class);
	private long key = makeUniqueKey();
	private String lastPostUrl  ;

	@Test(dataProvider = "contentTypesXml")
	public void getStores(String contentType) throws Exception {
		String url = storeUrl;
		//String contentType = CONTENT_XML ;
		RestHttpClient.Result result = httpClient.get(url,makeHeaders(contentType));
		assertStatus(result.statusCode);
		Assert.assertNotNull(result.response);
		String sresponse = new String(result.response);
		Object obj = readObject(contentType, sresponse,StoreList.class);
		Assert.assertTrue(obj instanceof StoreList);
		//StoreList storeList = (StoreList)obj;
	}

	@Test(dataProvider = "contentTypesXml")
	public void testPost_Get(String contentType) throws Exception {
		// Post the data
		Store store = Utils.createStore(maketRandomValue());
		String content = JaxbUtils.toString(store);
		String url = storeUrl;
		RestHttpClient.Result result = httpClient.post(url,content.getBytes(),makeHeaders(contentType));
		assertStatus(result);

		// Get URL of new resource from Location header
		String locationUrl = result.getHeaderValue("Location");
		Assert.assertNotNull(locationUrl);
		lastPostUrl = locationUrl;

		// Get the new URL
		result = httpClient.get(locationUrl,makeHeaders(contentType));
		assertStatus(result);
		Assert.assertNotNull(result.response);

		// Compare
		Store store2 = convertStore(contentType, result.response);
		Assert.assertEquals(store2.getId(),getId(locationUrl));
		Assert.assertEquals(store.getName(),store2.getName());
		Assert.assertEquals(store.getStoreId(),store2.getStoreId());
		Location location = store.getLocation();
		Location location2 = store2.getLocation();
		Assert.assertEquals(location.getStreet1(),location2.getStreet1());
		Assert.assertEquals(location.getStreet2(),location2.getStreet2());
		//Assert.assertEquals(location,location2); // Fails - hmm...
	}

	@Test(dataProvider = "contentTypesXml", dependsOnMethods="testPost_Get")
	public void testPut_Get(String contentType) throws Exception {
		RestHttpClient.Result result ;
		String url = lastPostUrl;

		Store store = Utils.createStore(maketRandomValue());
		String content = JaxbUtils.toString(store);

		result = httpClient.put(url,content.getBytes(),makeHeaders(contentType));
		assertStatus(result);

		result = httpClient.get(url,makeHeaders(contentType));
		assertStatus(result);
		Assert.assertNotNull(result.response);
		Store store2 = convertStore(contentType, result.response);

		Assert.assertEquals(store.getName(),store2.getName());
		Assert.assertEquals(store.getStoreId(),store2.getStoreId());
		Location location = store.getLocation();
		Location location2 = store2.getLocation();
		Assert.assertEquals(location.getStreet1(),location2.getStreet1());
		Assert.assertEquals(location.getStreet2(),location2.getStreet2());
	}

	@Test(dataProvider = "contentTypesXml")
	public void testPost_ValidationFailure(String contentType) throws Exception {
		Store store = Utils.createStore(null); // Name is required per schema - this will cause validation failure
		String content = JaxbUtils.toString(store);
		String url = storeUrl;
		RestHttpClient.Result result = httpClient.post(url,content.getBytes(),makeHeaders(contentType));

		Assert.assertEquals(result.statusCode,400);
		String sresponse = new String(result.response);
		Error error = convertError(contentType, result.response);
		Assert.assertEquals(error.getHttpStatusCode(),400);
		Assert.assertEquals(error.getApplicationCode(),ApplicationErrors.VALIDATION_FAILED);
	}

	@Test(dataProvider = "contentTypesXml")
	public void testPost_IllegalSyntax(String contentType) throws Exception {
		Store store = Utils.createStore(null); // Name is required per schema
		String content = "North End Church" ;
		String url = storeUrl;
		RestHttpClient.Result result = httpClient.post(url,content.getBytes(),makeHeaders(contentType));

		Assert.assertEquals(result.statusCode,400);
		String sresponse = new String(result.response);
		Error error = convertError(contentType, result.response);
		Assert.assertEquals(error.getHttpStatusCode(),400);
		Assert.assertEquals(error.getApplicationCode(),ApplicationErrors.ILLEGAL_SYNTAX);
	}

	private Store convertStore(String contentType, byte [] content) throws Exception {
		String scontent = new String(content); 
		Object obj = readObject(contentType, scontent,Store.class); // NOTE: Works for Jackson JSON but not for default JSON provider
		Assert.assertTrue(obj instanceof Store);
		return (Store)obj;
	}

	private Error convertError(String contentType, byte [] content) throws Exception {
		String scontent = new String(content); 
		//Object obj = readObject(contentType+"2", scontent, Error.class); // TODO: HACK to fix
		Object obj = readObject(contentType, scontent, Error.class); // TODO: HACK to fix
		Assert.assertTrue(obj instanceof Error);
		return (Error)obj;
	}

	public Long getId(String url) {
		String [] toks =  url.split("/");
		return Long.parseLong(toks[toks.length-1]);
	}
}
