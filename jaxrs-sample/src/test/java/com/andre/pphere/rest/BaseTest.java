package com.andre.pphere.rest;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import org.apache.commons.httpclient.Header;
import com.andre.http.RestHttpClient;

import com.andre.mapper.jaxb.JaxbObjectMapper;
import com.andre.mapper.jackson.JacksonObjectMapper;
import com.andre.mapper.ObjectMapper;

public class BaseTest {
	private static final Logger logger = Logger.getLogger(BaseTest.class);
	private static String [] configFiles = { 
		"appContext-test.xml",
	} ;
 	public static RestHttpClient httpClient = new RestHttpClient();
 	public static String baseUrl ;
	public static String storeUrl ;
 	private static TestConfig config ;
	public static File schemaFile = new File("config/schema.xsd");

	public static String pkg = "com.andre.pphere.data";
	public static String pkgRestData = "com.andre.rest.data";

	public static Random random = new Random(2012);
	public static final String CONTENT_JSON = "application/json" ;
	public static final String CONTENT_XML = "application/xml";

	public static ObjectMapper jsonMapper ;
	public static ObjectMapper xmlMapper ;
	private static Map<String,ObjectMapper> objectMappers = new HashMap<String,ObjectMapper>();

	@BeforeSuite
	public static void beforeSuite() throws Exception {
		initSpring();
		baseUrl = config.getBaseUrl();
		storeUrl = baseUrl + "/store" ;
 		Class clazz = com.andre.pphere.data.Store.class ;
		logger.debug("baseUrl="+baseUrl);

		jsonMapper = new JacksonObjectMapper();
		xmlMapper = new JaxbObjectMapper(schemaFile);
		objectMappers.put("application/json",jsonMapper);
		objectMappers.put("application/xml",xmlMapper);
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

	public static String maketRandomValue() {
		return ""+Math.abs(random.nextInt());
	}
	public static Long makeRandomLong() {
		return Math.abs(random.nextLong());
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

    public static void assertStatus(RestHttpClient.Result result, int statusCode) {
        Assert.assertEquals(result.statusCode, statusCode);
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

	@SuppressWarnings("unchecked")
	public Object readObject(String contentType, String content, Class clazz) throws Exception {
		ObjectMapper objectMapper = objectMappers.get(contentType);
		if (null == objectMapper)
			throw new Exception("No objectMapper for "+contentType);
		return objectMapper.toObject(content,clazz);
	}


	@DataProvider(name = "contentTypes") 
	public Object[][] contentTypes() {
		return new Object[][] {
			{ CONTENT_XML }
			,{ CONTENT_JSON }
		};
	}

	@DataProvider(name = "contentTypesXml") 
	public Object[][] contentTypesXml() {
		return new Object[][] {
			{ CONTENT_XML }
		};
	}
	@DataProvider(name = "contentTypesJson") 
	public Object[][] contentTypesJson() {
		return new Object[][] {
			{ CONTENT_JSON }
		};
	}

    @DataProvider(name = "objectMappers")
    public Object[][] objectMappers() {
        return new Object[][] {
            { jsonMapper }
            //,{ xmlMapper }
        };
    }
}
