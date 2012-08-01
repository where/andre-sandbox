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
	static File rootDir ;
	static JsonValidator validatorMain;
	static JsonValidator validatorRoot ;

	private static String [] configFiles = { 
		"appContext.xml",
	} ;

	@BeforeSuite
	public static void beforeSuite() throws Exception {
		initSpring();
		baseDir = new File(config.getBaseDir());
		rootDir = new File(baseDir,"basic");
		logger.debug("rootDir="+rootDir);
		logger.debug("config="+config);
		logger.debug("schemaFile="+schemaFile);
		schemaFile = new File(rootDir,"schema.json");
		Assert.assertTrue(schemaFile.exists());
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

	public void testOk(JsonValidator validator, File instanceFile) throws Exception {
		List<String> results = validator.validate(instanceFile);
		String emsg = CollectionUtils.toString(results);
		Assert.assertEquals(results.size(),0,emsg);
		JsonValidatorUtils.report(instanceFile, results);
	}

	public void testBad(File instanceFile) throws Exception {
		testBad(validatorMain,instanceFile);
	}

	public void testBad(JsonValidator validator,File instanceFile) throws Exception {
		List<String> results = validator.validate(instanceFile);
		JsonValidatorUtils.report(instanceFile, results);
		//Assert.assertTrue(results.size() > 0);
		Assert.assertTrue(results.size() == 1);
	}

	public Object[][] createFiles(File dir) {
		Assert.assertNotNull(dir,"Directory is null");
		Assert.assertTrue(dir.exists(),"Directory "+dir.getAbsolutePath()+" does not exist");
		String [] filenames = dir.list(new MyFilenameFilter());
		Object[][] objects = new Object[filenames.length][1];
		int j=0;
		for (String filename : filenames)
			objects[j++][0] = filename;
		return objects;
	}

	void assertDir(File dir) {
		Assert.assertTrue(dir.exists(),"Directory "+dir.getAbsolutePath()+" does not exist");
	}


	class MyFilenameFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.endsWith(".json");
		}
	}

}
