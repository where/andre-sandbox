package com.andre.jsonschema;

import com.google.common.io.Files;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import uk.co.o2.json.schema.JsonSchema;
import uk.co.o2.json.schema.ErrorMessage;;
import uk.co.o2.json.schema.SchemaPassThroughCache;;
import java.util.*;
import java.io.*;
import java.net.*;
import org.apache.log4j.Logger;

public class EqualExpertsJsonValidator implements JsonValidator {
	private static final Logger logger = Logger.getLogger(EqualExpertsJsonValidator.class);
	private JsonFactory jsonFactory = new JsonFactory(new ObjectMapper());
	private SchemaPassThroughCache schemaFactory = new SchemaPassThroughCache(jsonFactory);
	private JsonSchema schema ;

	public EqualExpertsJsonValidator() {
	}

	public EqualExpertsJsonValidator(File schemaFile) throws IOException {
		if (!schemaFile.exists()) 
			throw new IOException("Schema file "+schemaFile+" does not exist");
		URL url = schemaFile.toURL();
		logger.debug("url="+url);
		if (url == null) 
			throw new IOException("Schema file "+schemaFile+" is not URL");
		schema = schemaFactory.getSchema(url);
	}

	public List<String> validate(File instanceFile) throws IOException {
		if (schema == null)
			throw new IOException("No schema");

		if (!instanceFile.exists()) {
			throw new IOException("Instance file "+instanceFile+" does not exist");
		}

		URL url = instanceFile.toURL();
		if (url == null) 
			throw new IOException("Instance file "+instanceFile+" is not URL");
		JsonNode json = jsonFactory.createJsonParser(url).readValueAsTree();
		return validate(json);
	}

	public List<String> validate(String json) throws IOException {
		JsonNode jsonNode = jsonFactory.createJsonParser(json).readValueAsTree();
		return validate(jsonNode) ;
	}

	public List<String> validate(JsonNode json) throws IOException {

		List<ErrorMessage> errors = schema.validate(json);
		List<String> errs = new ArrayList<String>() ;
		for (ErrorMessage error : errors) {
			errs.add(error.toString());
		}
		return errs;
	}

	public EqualExpertsJsonValidator createInstance(File schemaFile) throws IOException {
		return new EqualExpertsJsonValidator(schemaFile);
	}
}
