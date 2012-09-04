package com.paypal.ppmn.rps.client;

import com.paypal.ppmn.rps.BaseTest;
import com.paypal.ppmn.rps.data.Profile;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class ThreadLoadTest extends BaseClientTest {
	private static Logger logger = Logger.getLogger(ThreadLoadTest.class);
	private String keyBase = "load-client-key" ;
	private static final int invocationCount = 50 ;
	private static final int threadPoolSize = 10 ;
	private final static AtomicInteger keyCount = new AtomicInteger(0);

	@Test(threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
    public void put() throws Exception {
		String key = makeKey();
        Profile profile = newProfile(key, value) ;
        client.save(profile);
    }

	@Test (dependsOnMethods="put")
	public void getInit() throws Exception {
		keyCount.set(0);
	}

	@Test (dependsOnMethods="getInit", threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
	public void get() throws Exception {
		String key = makeKey();
        Profile profile = client.get(key);
        Assert.assertNotNull(profile);
        //assertProfile(profile,profileRef);
	}

	@Test (dependsOnMethods="get")
	public void deleteInit() throws Exception {
		keyCount.set(0);
	}

	@Test (dependsOnMethods="deleteInit", threadPoolSize = threadPoolSize, invocationCount = invocationCount,  timeOut = 10000)
	public void delete() throws Exception {
		String key = makeKey();
        client.delete(key);
        Profile profile = client.get(key);
        Assert.assertNull(profile);
    }

	private String makeKey() {
		return keyBase+"-"+keyCount.getAndIncrement() ;
	}
}
