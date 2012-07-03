package com.andre.jsonschema;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.andre.util.CollectionUtils;

public class BaseTest {
	private static final Logger logger = Logger.getLogger(BaseTest.class);
 	static TestConfig config ;
	static File schemaFile;
	static File baseDir ;
	static JsonValidator validatorMain;
	static JsonValidator validatorRoot ;

	private static String [] configFiles = { 
		"appContext.xml",
	} ;

	@BeforeSuite
	public static void beforeSuite() throws Exception {
		initSpring();
		baseDir = new File(config.getBaseDir());
		schemaFile = new File(config.getSchemaFile());
		logger.debug("config="+config);
		logger.debug("schemaFile="+schemaFile);
		validatorMain = validatorRoot.createInstance(schemaFile);
	}

	@AfterSuite
	public static void afterSuite() {
	}

	static void initSpring() {
		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		config = context.getBean("testConfig",TestConfig.class);
		validatorRoot = context.getBean("validator",JsonValidator.class);
		logger.debug("validatorRoot="+validatorRoot);
	}

	public static Object[][] createObjectArray(Collection coll) {
		Assert.assertNotNull(coll,"Internal error - BaseTest.createObjectArray cannot accept null collection");
		Object[][] objects = new Object[coll.size()][1];
		int j=0;
		for (Object obj : coll)
			objects[j++][0] = obj ;
		return objects;
	}

	public void testOk(JsonValidator validator, File instanceFile) throws Exception {
		List<String> results = validator.validate(instanceFile);
		String emsg = CollectionUtils.toString(results);
		Assert.assertEquals(results.size(),0,emsg);
		JsonValidatorUtils.report(instanceFile, results);
	}

	public void testBad(File instanceFile) throws Exception {
		List<String> results = validatorMain.validate(instanceFile);
		JsonValidatorUtils.report(instanceFile, results);
		//Assert.assertTrue(results.size() > 0);
		Assert.assertTrue(results.size() == 1);
	}
}
