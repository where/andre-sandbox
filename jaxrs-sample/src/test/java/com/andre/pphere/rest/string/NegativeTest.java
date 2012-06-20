package com.andre.pphere.rest.string;

import java.util.*;
import java.io.*;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.pphere.data.*;
import com.andre.http.RestHttpClient;
import com.andre.pphere.rest.BaseTest;

public class NegativeTest extends BaseTest {

	@Test
	public void getBadUrl() throws Exception {
		String url = baseUrl + "/foo" ;
		RestHttpClient.Result result = httpClient.get(url);
		Assert.assertEquals(result.statusCode,404);
	}
	@Test
	public void deleteBadUrl() throws Exception {
		String url = baseUrl + "/foo" ;
		RestHttpClient.Result result = httpClient.delete(url);
		Assert.assertEquals(result.statusCode,404);
	}

	@Test
	public void illegalAcceptHeader_GET() throws Exception {
		String url = storeUrl;
		String contentType = "text/plain" ;
		RestHttpClient.Result result = httpClient.get(url,makeHeaders(contentType));
		Assert.assertEquals(result.statusCode,406); 
	}

	@Test
	public void illegalAcceptHeader_POST() throws Exception {
		String url = storeUrl;
		String contentType = "text/plain" ;
		String content = "Beware of Denali's West Buttress" ;
		RestHttpClient.Result result = httpClient.post(url,content.getBytes(),makeHeaders(contentType));
		Assert.assertEquals(result.statusCode,406); 
	}
}
