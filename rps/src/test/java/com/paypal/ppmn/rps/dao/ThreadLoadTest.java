package com.paypal.ppmn.rps.dao;

import com.paypal.ppmn.rps.BaseTest;
import com.paypal.ppmn.rps.data.Profile;
import com.paypal.ppmn.rps.data.FormatUtils;
import java.util.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class ThreadLoadTest extends BaseTest {
	private static Logger logger = Logger.getLogger(ThreadLoadTest.class);
	private String keyBase = "load-key" ;
	private static final int invocationCount = 500 ;
	private static final int threadPoolSize = 10 ;
	private final static AtomicInteger keyCount = new AtomicInteger(0);

	@Test(threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
	public void put() throws Exception {
		String value = "value" ;
		String key = makeKey();
		Profile profile = newProfile(key, value) ;
		//logger.debug("key="+key+" profile="+profile);
		profileService.save(profile);
	}

	@Test (dependsOnMethods="put")
	public void getInit() throws Exception {
		keyCount.set(0);
	}

	@Test (dependsOnMethods="getInit", threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
	public void get() throws Exception {
		String key = makeKey();
		Profile profile = profileService.get(key);
		//logger.debug("key="+key+" profile="+profile);
		Assert.assertNotNull(profile,"key="+key);
	}


	@Test (dependsOnMethods="get")
	public void deleteInit() throws Exception {
		keyCount.set(0);
	}

	@Test (dependsOnMethods="deleteInit", threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
	public void deleteGet() throws Exception {
		String key = makeKey();
		profileService.delete(key);
		Profile profile = profileService.get(key);
		Assert.assertNull(profile);
	}

	String makeKey() {
		return keyBase+"-"+keyCount.getAndIncrement() ;
	}
}
