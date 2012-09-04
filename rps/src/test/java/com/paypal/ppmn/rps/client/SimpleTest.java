package com.paypal.ppmn.rps.client;

import com.paypal.ppmn.rps.BaseTest;
import com.paypal.ppmn.rps.data.Profile;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class SimpleTest extends BaseClientTest {
	private static Logger logger = Logger.getLogger(SimpleTest.class);
	private String key = "client-key" ;
	private String value = "client-value" ;
	private Profile profileRef ;

	@Test
	public void put() throws Exception {
        Profile profile = newProfile(key, value) ;
		client.save(profile);
        profileRef = profile ;
	}

	@Test (dependsOnMethods="put")
	public void get() throws Exception {
		Profile profile = client.get(key);
		Assert.assertNotNull(profile);
		assertProfile(profile,profileRef);
	}

	@Test (dependsOnMethods="get")
	public void deleteGet() throws Exception {
		client.delete(key);
		Profile profile = client.get(key);
		Assert.assertNull(profile);
	}
}
