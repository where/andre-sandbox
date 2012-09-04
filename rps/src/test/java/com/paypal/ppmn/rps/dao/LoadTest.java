package com.paypal.ppmn.rps.dao;

import com.paypal.ppmn.rps.BaseTest;
import com.paypal.ppmn.rps.data.Profile;
import com.paypal.ppmn.rps.data.FormatUtils;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoadTest extends BaseTest {
	private static Logger logger = Logger.getLogger(LoadTest.class);
	private String keyBase = "load-key" ;
	private int count = 10 ;

	@Test
	public void put() throws Exception {
		String value = "value" ;
		for (int j=0 ; j < count ; j++) {
			String key = makeKey(j);
			Profile profile = newProfile(key, value) ;
			//logger.debug("key="+key+" profile="+profile);
			profileService.save(profile);
		}
	}

	@Test (dependsOnMethods="put")
	public void get() throws Exception {
		for (int j=0 ; j < count ; j++) {
			String key = makeKey(j);
			Profile profile = profileService.get(key);
			//logger.debug("key="+key+" profile="+profile);
			Assert.assertNotNull(profile,"key="+key);
			// assertProfile(profile,profile2);
		}
	}

	@Test (dependsOnMethods="get")
	public void deleteGet() throws Exception {
		for (int j=0 ; j < count ; j++) {
			String key = makeKey(j);
			profileService.delete(key);
			Profile profile = profileService.get(key);
			Assert.assertNull(profile);
		}
	}

	String makeKey(int num) {
		return keyBase + "-"+num;
	}
}
