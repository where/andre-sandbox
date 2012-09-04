package com.paypal.ppmn.rps.api;

import com.paypal.ppmn.rps.BaseTest;
import com.paypal.ppmn.rps.data.Profile;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.http.RestHttpClient;

public class ThreadLoadTest extends BaseRestTest {
	private static Logger logger = Logger.getLogger(ThreadLoadTest.class);
	private String keyBase = "load-rest-key" ;
	private static final int invocationCount = 50 ;
	private static final int threadPoolSize = 10 ;
	private final static AtomicInteger keyCount = new AtomicInteger(0);

	@Test(threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
	public void put() throws Exception {
		String key = makeKey();
		String url = makeUrl(key);
		Profile profile = newProfile(key, value) ;

		byte [] content = objectMapper.toBytes(profile);
		RestHttpClient.Result result = httpClient.put(url,content);
		assertStatus(result.statusCode);
	}

	@Test (dependsOnMethods="put")
	public void getInit() throws Exception {
		keyCount.set(0);
	}

	@Test (dependsOnMethods="getInit", threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
	public void get() throws Exception {
		String url = makeUrl(makeKey());

		RestHttpClient.Result result = httpClient.get(url);
		assertStatus(result.statusCode);
		Assert.assertNotNull(result.response);

		//String svalue = new String(result.response);
		//logger.debug("profile: "+svalue);
	}

	@Test (dependsOnMethods="get")
	public void deleteInit() throws Exception {
		keyCount.set(0);
	}

	@Test (dependsOnMethods="deleteInit", threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
	public void delete() throws Exception {
		String url = makeUrl(makeKey());
		RestHttpClient.Result result = httpClient.delete(url);
		assertStatus(result.statusCode);

		result = httpClient.get(url);
		Assert.assertEquals(result.statusCode,404);
	}

	private String makeKey() {
		return keyBase+"-"+keyCount.getAndIncrement() ;
	}
}
