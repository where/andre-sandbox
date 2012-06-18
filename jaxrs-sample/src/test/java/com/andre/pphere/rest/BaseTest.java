package com.andre.pphere.rest;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.amm.httpclient.ByteHttpClient;
import org.apache.commons.httpclient.Header;
import com.andre.http.RestHttpClient;
import com.andre.rest.reader.jaxb.JaxbReader;
import com.andre.rest.reader.json.JsonReader;
import com.andre.rest.reader.ObjectReader;

public class BaseTest {
	private static final Logger logger = Logger.getLogger(BaseTest.class);
	private static String [] configFiles = { 
		"appContext-test.xml",
	} ;
 	static ByteHttpClient byteHttpClient = new ByteHttpClient();
 	static RestHttpClient httpClient = new RestHttpClient();
 	public static String baseUrl ;
	static String storeUrl ;
 	private static TestConfig config ;
	private static File schemaFile = new File("config/schema.xsd");
	private static String pkg = "com.andre.pphere.data";
	private static Map<String,ObjectReader> objectReaders = new HashMap<String,ObjectReader>();

	@BeforeSuite
	public static void beforeSuite() throws Exception {
		initSpring();
		baseUrl = config.getBaseUrl();
		storeUrl = baseUrl + "/store" ;
 		Class clazz = com.andre.pphere.data.Store.class ;
		objectReaders.put("application/json",new JsonReader(clazz));
		objectReaders.put("application/xml",new JaxbReader(schemaFile,clazz,pkg));
		logger.debug("baseUrl="+baseUrl);
	}

	@AfterSuite
	public static void afterSuite() {
	}

	@SuppressWarnings("unchecked")
	static void initSpring() throws Exception {
		logger.debug("configFiles="+Arrays.toString(configFiles));
		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		config = context.getBean("testConfig",TestConfig.class);
	}

	public static Object[][] createObjectArray(Collection coll) {
		Assert.assertNotNull(coll,"Internal error - BaseTest.createObjectArray cannot accept null collection");
		Object[][] objects = new Object[coll.size()][1];
		int j=0;
		for (Object obj : coll)
			objects[j++][0] = obj ;
		return objects;
	}

	public static String makeKeyUrl(long key) {
		return baseUrl + "/" + key;
	}

	public static long makeUniqueKey() {
		 return System.currentTimeMillis() ;
	}

	public static void assertStatus(int statusCode) {
		Assert.assertTrue(!isError(statusCode),"StatusCode="+statusCode);
		//Assert.assertFalse(isError(statusCode));
	}

	public static void assertStatus(RestHttpClient.Result result) {
		String msg = "ERROR: StatusCode="+result.statusCode
			+ " StatusLine="+result.method.getStatusLine();
		//Assert.assertFalse(isError(result.statusCode),msg);
		Assert.assertTrue(!isError(result.statusCode),msg);
	}


	public static boolean isError(int statusCode) {
		return statusCode < 200 || statusCode > 299 ;
	}

	public static Header [] makeHeaders(String contentType) {
		Header [] headers = new Header[2];
		headers[0] = new Header("Accept",contentType);
		headers[1] = new Header("content-type",contentType);
		return headers ;
	}

	public Object readObject(String contentType, String content) throws Exception {
		ObjectReader objectReader = objectReaders.get(contentType);
		if (null == objectReader)
			throw new Exception("No objectReader for "+contentType);
		return objectReader.read(content);
	}

	public static ContentFactory contentFactory = new ContentFactory();
}
