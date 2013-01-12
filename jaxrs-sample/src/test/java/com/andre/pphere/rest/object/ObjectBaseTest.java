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
import com.andre.pphere.rest.BaseTest;

/**
 * For now, only using XML content - JSON under way.
 * JSON marashalling fails - plain Jackson doesn't match CXF Jackson provider with JAXB.
 */
public class ObjectBaseTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(ObjectBaseTest.class);

	public Store convertStore(String contentType, byte [] content) throws Exception {
		String scontent = new String(content); 
		Object obj = readObject(contentType, scontent,Store.class); // NOTE: Works for Jackson JSON but not for default JSON provider
		Assert.assertTrue(obj instanceof Store);
		return (Store)obj;
	}

	public Error convertError(String contentType, byte [] content) throws Exception {
		String scontent = new String(content); 
		Object obj = readObject(contentType, scontent, Error.class); // TODO: HACK to fix
		Assert.assertTrue(obj instanceof Error);
		return (Error)obj;
	}

	public Long extractId(String url) {
		String [] toks =  url.split("/");
		return Long.parseLong(toks[toks.length-1]);
	}

	@AfterClass
	void afterClass() throws Exception {
		for (String url : urlsPosted) {
			//logger.debug("url="+url);
			RestHttpClient.Result result = httpClient.delete(url) ;
			Assert.assertEquals(result.statusCode,200);
		}
	}

	private List<String> urlsPosted = new ArrayList<String>();

	public RestHttpClient.Result post(String storeUrl, String content) throws Exception {
		return post(storeUrl, content, null) ;
	}

	public RestHttpClient.Result post(String storeUrl, String content, String contentType) throws Exception {
		RestHttpClient.Result result ;
		if (contentType == null)
			result = httpClient.post(storeUrl,content.getBytes());
		else
			result = httpClient.post(storeUrl,content.getBytes(),makeHeaders(contentType));

        String locationUrl = result.getHeaderValue("Location");
		//logger.debug("locationUrl="+locationUrl);
		if (locationUrl != null)
			urlsPosted.add(locationUrl);
		return result ;
	}
}
