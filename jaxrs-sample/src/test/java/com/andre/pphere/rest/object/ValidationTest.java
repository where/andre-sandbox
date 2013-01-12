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
public class ValidationTest extends ObjectBaseTest {
	private static final Logger logger = Logger.getLogger(ValidationTest.class);
	private long key = makeUniqueKey();
	private String lastPostUrl  ;

	// JSON: fails but shouldn't
	@Test(dataProvider = "objectMappers")
	public void testPost_ValidationFailure_RequiredField(ObjectMapper mapper) throws Exception {
		String contentType = mapper.getContentType();
		Store store = Utils.createStore(null); // Name is required per schema - this will cause validation failure
		String content = mapper.toString(store);
		RestHttpClient.Result result = post(storeUrl,content,contentType);

		int statusCode = 400 ;
		Assert.assertEquals(result.statusCode,statusCode);
		String sresponse = new String(result.response);
		Error error = convertError(contentType, result.response);
		Assert.assertEquals(error.getHttpStatusCode(),statusCode);
		Assert.assertEquals(error.getApplicationCode(),ApplicationErrors.VALIDATION_FAILED);
	}

	@Test(dataProvider = "objectMappers")
	public void testPost_ValidationFailure_BadValue(ObjectMapper mapper) throws Exception {
		String contentType = mapper.getContentType();
		Store store = Utils.createStore("foo"); 
		
		long value = random.nextLong();
		store.setId(value);
		String content = mapper.toString(store);
		content = content.replace(""+value,"\"NON_NUMERIC\"");
		RestHttpClient.Result result = post(storeUrl,content,contentType);

		int statusCode = 400 ;
		Assert.assertEquals(result.statusCode,statusCode);
		String sresponse = new String(result.response);
		Error error = convertError(contentType, result.response);
		Assert.assertEquals(error.getHttpStatusCode(),statusCode);
		Assert.assertEquals(error.getApplicationCode(),ApplicationErrors.VALIDATION_FAILED);
	}

}
