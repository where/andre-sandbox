package com.andre.jsonschema;

import com.google.common.io.Files;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

public class JsonValidatorDriver {
	private static final Logger logger = Logger.getLogger(JsonValidatorDriver.class);
	private JsonValidator validator;
	private String [] configFiles = { "appContext.xml", } ;
	private String validatorPropName = "cfg.jsonValidator" ;
	private String validatorBeanName ;

	public static void main(String [] args) throws Exception {
		(new JsonValidatorDriver()).process(args);
	}

	public void process(String [] args) throws Exception {
		if (args.length < 2) {
			error("Expecting SCHEMA_FILE INSTANCE_FILE");
			return;
		}
		initSpring();
		validate(new File(args[0]), new File(args[1])) ;
		logger.debug("validator="+validator.getClass().getName());
	}

	public void validate(File schemaFile, File instanceFile) throws Exception {
		logger.debug("Schema file="+schemaFile);
		logger.debug("Instance file="+instanceFile);

		if (!schemaFile.exists()) {
			throw new IOException("Schema file "+schemaFile+" does not exist");
		}
		validator = validator.createInstance(schemaFile);
		logger.debug("Validator="+validator);

		if (!instanceFile.exists()) {
			throw new IOException("Instance file "+instanceFile+" does not exist");
		}

		String schema = new String(Files.toByteArray(schemaFile));
		String json = new String(Files.toByteArray(instanceFile));
		List<String> results = validator.validate(instanceFile);
		JsonValidatorUtils.report(instanceFile, results);
	}

	void initSpring() {
		String pval = System.getProperty(validatorPropName);
		logger.debug(validatorPropName+"="+pval);

		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		validator = context.getBean("validator",JsonValidator.class);
		logger.debug("validator="+validator.getClass().getName());
	}
	
	void error(Object o) { System.out.println("ERROR: "+o);}
}
