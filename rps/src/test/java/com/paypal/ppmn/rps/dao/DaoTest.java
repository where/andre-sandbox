package com.paypal.ppmn.rps.dao;

import com.paypal.ppmn.rps.BaseTest;
import com.paypal.ppmn.rps.data.Profile;
import com.paypal.ppmn.rps.data.FormatUtils;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class DaoTest extends BaseTest {
	private static Logger logger = Logger.getLogger(DaoTest.class);
	private String key = "test-key" ;

	@Test
	public void putGet() throws Exception {
		String value = "value" ;

		Profile profile = newProfile(key, value) ;
		FormatUtils.format(profile,"Profile1");
		profileService.save(profile);

		Profile profile2 = profileService.get(key);
		FormatUtils.format(profile2,"Profile2");
		assertProfile(profile,profile2);
	}

	@Test (dependsOnMethods="putGet")
	public void deleteGet() throws Exception {
		profileService.delete(key);
		Profile profile = profileService.get(key);
		Assert.assertNull(profile);
	}

}
