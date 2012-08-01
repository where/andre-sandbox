package com.andre.jsonschema;

import com.google.common.io.Files;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.andre.mapper.ObjectMapper;
import com.andre.mapper.jackson.JacksonObjectMapper;
import com.where.pphere.api.data.*;

public class ObjectValidatorDriver {
/* TODO
	ObjectMapper mapper = new JacksonObjectMapper(true);
	private static final Logger logger = Logger.getLogger(ObjectValidatorDriver.class);
	private JsonValidator validator;
	private String [] configFiles = { "appContext.xml", } ;
	private String validatorPropName = "cfg.jsonValidator" ;
	private String validatorBeanName ;

	public static void main(String [] args) throws Exception {
		(new ObjectValidatorDriver()).process(args);
	}

	public void process(String [] args) throws Exception {
		if (args.length < 1) {
			error("Expecting SCHEMA_FILE");
			return;
		}
		initSpring();

		process(new File(args[0]));
	}

	public void process(File schemaFile) throws Exception {
		Object obj = getObject() ;
		String content = mapper.toString(obj);
		validate(schemaFile, content) ;
	}

	public void validate(File schemaFile, String content) throws Exception {
		logger.debug("Schema file="+schemaFile);
		logger.debug("content="+content);

		if (!schemaFile.exists()) {
			throw new IOException("Schema file "+schemaFile+" does not exist");
		}
		validator = validator.createInstance(schemaFile);
		logger.debug("Validator="+validator);

		String schema = new String(Files.toByteArray(schemaFile));
		List<String> results = validator.validate(content);
		JsonValidatorUtils.report("Result", results);
	}

	void initSpring() {
		String pval = System.getProperty(validatorPropName);
		logger.debug(validatorPropName+"="+pval);

		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		validator = context.getBean("validator",JsonValidator.class);
		logger.debug("validator="+validator.getClass().getName());
	}

	Object getObject() {
		//Location obj = new Location();
		//Address obj = new Address();
		Store obj = new Store();
		return obj ;
	}
	
	void error(Object o) { System.out.println("ERROR: "+o);}
*/
}
