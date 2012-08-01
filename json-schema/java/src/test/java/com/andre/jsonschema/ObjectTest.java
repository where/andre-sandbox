package com.andre.jsonschema;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class ObjectTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(ObjectTest.class);
	private File badDir ;
	private File okDir ;
	private File rootDir ;
	private JsonValidator validator ;

	@BeforeClass
	void beforeClass() throws Exception {
		rootDir = new File(baseDir,"object");
		badDir = new File(rootDir,"badfiles");
		okDir = new File(rootDir,"okfiles");
		Assert.assertTrue(okDir.exists());
		Assert.assertTrue(badDir.exists());

		File schemaFile = new File(rootDir,"schema-store.json");
		Assert.assertTrue(schemaFile.exists());
		validator = validatorRoot.createInstance(schemaFile);

	}

	@DataProvider(name = "okFiles") 
	public Object[][] okFiles() {
		return createFiles(okDir) ;
	}

	@DataProvider(name = "badFiles") 
	public Object[][] badFiles() {
		return createFiles(badDir) ;
	}

	@Test(dataProvider = "okFiles")
	public void testOkFile(String filename) throws Exception {
		testOk(validator,new File(okDir,filename));
	}

	@Test(dataProvider = "badFiles")
	public void testBadFile(String filename) throws Exception {
		testBad(validator,new File(badDir,filename));
	}

	// NOTE: for equalExpertsJsonValidator
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testMissingAddressSchemaFile() throws Exception {
		File schemaFile = new File(rootDir,"schema-store-bad-address-schema.json");
		Assert.assertTrue(schemaFile.exists());
		JsonValidator validatorBad = validatorRoot.createInstance(schemaFile);
	}
}
