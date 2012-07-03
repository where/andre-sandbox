package com.andre.jsonschema;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

@Test(groups = { "ref" })
public class RefSchemaTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(BaseTest.class);

	private File schemaFileRef ;
	JsonValidator validatorRef ;

	@BeforeClass
	void beforeClass() throws IOException {

		schemaFileRef = new File(config.getSchemaFileRef());
		logger.debug("schemaFileRef="+schemaFileRef);
		validatorRef = validatorRoot.createInstance(schemaFileRef);
	}

	@Test
	public void testOkRef() throws Exception {
		testOk(validatorRef, new File(baseDir,schemaFile.getName())) ;
	}
}
