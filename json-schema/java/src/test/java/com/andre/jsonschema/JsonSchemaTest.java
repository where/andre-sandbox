package com.andre.jsonschema;

import java.util.*;
import java.io.*;
import org.testng.Assert;
import org.testng.annotations.*;

public class JsonSchemaTest extends BaseTest {
	private File badDir ;
	private File okDir ;

	@BeforeClass
	void beforeClass() {
		badDir = new File(baseDir,"badfiles");
		okDir = new File(baseDir,"okfiles");
	}

	@DataProvider(name = "okFiles") 
	public Object[][] okFiles() {
		return createFiles(okDir) ;
	}

	@DataProvider(name = "badFiles") 
	public Object[][] badFiles() {
		return createFiles(badDir) ;
	}

	public Object[][] createFiles(File dir) {
		Assert.assertTrue(dir.exists(),"Directory "+dir.getAbsolutePath()+" does not exist");
		String [] filenames = dir.list(new MyFilenameFilter());
		Object[][] objects = new Object[filenames.length][1];
		int j=0;
		for (String filename : filenames)
			objects[j++][0] = filename;
		return objects;
	}

	class MyFilenameFilter implements FilenameFilter {
		public boolean accept(File dir, String name) {
			return name.endsWith(".json");
		}
	}

	@Test(dataProvider = "okFiles")
	public void testOkFile(String filename) throws Exception {
		testOk(validatorMain,new File(okDir,filename));
	}

	@Test(dataProvider = "badFiles")
	public void testBadFile(String filename) throws Exception {
		testBad(new File(badDir,filename));
	}

	@Test(groups = { "ref" })
	public void testOkRef() throws Exception {
		testOk(validatorRef, new File(baseDir,schemaFile.getName())) ;
	}
}
