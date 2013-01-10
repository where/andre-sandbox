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
import com.andre.mapper.ObjectMapper;

/**
 * For now, only using XML content - JSON under way.
 * JSON marashalling fails - plain Jackson doesn't match CXF Jackson provider with JAXB.
 */
public class ObjectTest extends ObjectBaseTest {
	private static final Logger logger = Logger.getLogger(ObjectTest.class);
	private long key = makeUniqueKey();
	private String lastPostUrl  ;

	// JSON: fails since JAXB/Jackson emits 'store' instead of 'stores' which plain Jackson expects
	@Test(dataProvider = "objectMappers")
	public void getStores(ObjectMapper mapper) throws Exception {
		String contentType = mapper.getContentType();
		RestHttpClient.Result result = httpClient.get(storeUrl,makeHeaders(contentType));
		assertStatus(result.statusCode);
		Assert.assertNotNull(result.response);
		String sresponse = new String(result.response);
		Object obj = readObject(contentType, sresponse,StoreList.class);
		Assert.assertTrue(obj instanceof StoreList);
		//StoreList storeList = (StoreList)obj;
	}

	@Test(dataProvider = "objectMappers")
	public void testPost_Get(ObjectMapper mapper) throws Exception {
		String contentType = mapper.getContentType();
		// Post the data
		Store store = Utils.createStore(maketRandomValue());
		String content = mapper.toString(store);
		RestHttpClient.Result result = httpClient.post(storeUrl,content.getBytes(),makeHeaders(contentType));
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
		Assert.assertEquals(store2.getId(),extractId(locationUrl));
		Assert.assertEquals(store.getName(),store2.getName());
		Assert.assertEquals(store.getStoreId(),store2.getStoreId());
		Location location = store.getLocation();
		Location location2 = store2.getLocation();
		Assert.assertEquals(location.getStreet1(),location2.getStreet1());
		Assert.assertEquals(location.getStreet2(),location2.getStreet2());
		//Assert.assertEquals(location,location2); // Fails - hmm...
	}

	@Test(dataProvider = "objectMappers", dependsOnMethods="testPost_Get")
	public void testPut_Get(ObjectMapper mapper) throws Exception {
		String contentType = mapper.getContentType();
		RestHttpClient.Result result ;
		String url = lastPostUrl;

		Store store = Utils.createStore(maketRandomValue());
		String content = mapper.toString(store);

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

    @Test(dataProvider = "objectMappers", dependsOnMethods="testPut_Get")
    public void testDelete_Get(ObjectMapper mapper) throws Exception {
        String contentType = mapper.getContentType();
        RestHttpClient.Result result ;
        String url = lastPostUrl;

        result = httpClient.get(url,makeHeaders(contentType));
        assertStatus(result);

        result = httpClient.delete(url);
        assertStatus(result);

        result = httpClient.get(url,makeHeaders(contentType));
        Assert.assertEquals(result.method.getStatusCode(), 404);
    } 


	@Test(dataProvider = "objectMappers")
	public void testPost_IllegalSyntax(ObjectMapper mapper) throws Exception {
		String contentType = mapper.getContentType();
		Store store = Utils.createStore(null); // Name is required per schema
		String content = "North End Church" ;
		RestHttpClient.Result result = post(storeUrl,content,contentType);

		Assert.assertEquals(result.statusCode,400);
		String sresponse = new String(result.response);
		Error error = convertError(contentType, result.response);
		Assert.assertEquals(error.getHttpStatusCode(),400);
		Assert.assertEquals(error.getApplicationCode(),ApplicationErrors.ILLEGAL_SYNTAX);
	}

	@Test
	public void testPost_UnsupportedMedia() throws Exception {
		String contentType = "text/plain" ;
		String content = "North End Church" ;
		RestHttpClient.Result result = post(storeUrl,content,contentType);
        Assert.assertEquals(result.statusCode,406);
	}

	@Test
	public void testPost_IllegalAcceptHeader() throws Exception {
		String content = "North End Church" ;
		String contentType = "foobar";
		RestHttpClient.Result result = post(storeUrl,content,contentType);
        Assert.assertEquals(result.statusCode,406);
	}
}
