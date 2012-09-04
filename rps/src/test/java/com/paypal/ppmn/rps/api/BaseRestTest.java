package com.paypal.ppmn.rps.api;

import com.paypal.ppmn.rps.BaseTest;
import com.paypal.ppmn.rps.data.Profile;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.http.RestHttpClient;
import com.andre.util.TestNgUtils;

public class BaseRestTest extends BaseTest {
	private static Logger logger = Logger.getLogger(BaseRestTest.class);
	public static RestHttpClient httpClient = new RestHttpClient();
	public static String profileUrl ;
	public String value = "bar";

	@BeforeClass
	public static void beforeClass() throws Exception {
		profileUrl = baseUrl + "/profile" ;
	}

	//public static void assertStatus(int statusCode) {
		//TestUtils.assertStatus(statusCode);
	//}

	public static void assertStatus(int statusCode) {
		Assert.assertFalse(isError(statusCode));
	}
	public static boolean isError(int statusCode) {
		return statusCode < 200 || statusCode > 299 ;
	}

	String makeUrl(String key) {
		return profileUrl + "/" + key;
	}
}

