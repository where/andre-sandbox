package com.andre.jsonschema;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class JsonSchemaTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(JsonSchemaTest.class);
	private File badDir ;
	private File okDir ;
	private File optionalFormatDir ;
	private boolean optionalFormatSupported = false ;

	@BeforeClass
	void beforeClass() {
		badDir = new File(rootDir,"badfiles");
		okDir = new File(rootDir,"okfiles");
		optionalFormatDir = new File(rootDir,"optional-format");
		assertDir(badDir);
		assertDir(okDir);
		assertDir(optionalFormatDir);
		logger.debug("optionalFormatSupported="+optionalFormatSupported);
	}

	@DataProvider(name = "okFiles") 
	public Object[][] okFiles() {
		return createFiles(okDir) ;
	}

	@DataProvider(name = "badFiles") 
	public Object[][] badFiles() {
		return createFiles(badDir) ;
	}

	@DataProvider(name = "optionalFormatFiles") 
	public Object[][] optionalFormatFiles() {
		return createFiles(optionalFormatDir) ;
	}

	@Test(dataProvider = "okFiles")
	public void testOkFile(String filename) throws Exception {
		testOk(validatorMain,new File(okDir,filename));
	}

	@Test(dataProvider = "badFiles")
	public void testBadFile(String filename) throws Exception {
		testBad(new File(badDir,filename));
	}

	@Test(dataProvider = "optionalFormatFiles")
	public void testOptionalFormatFiles(String filename) throws Exception {
		if (optionalFormatSupported)
			testBad(new File(optionalFormatDir,filename));
		else
			testOk(validatorMain,new File(optionalFormatDir,filename));
	}
}
