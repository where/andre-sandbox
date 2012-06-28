package com.andre.jsonschema;

import com.google.common.io.Files;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import eu.vahlas.json.schema.impl.JacksonSchema;
import eu.vahlas.json.schema.JSONSchemaProvider;
import java.util.*;
import java.io.*;

public class VahlasJsonValidator implements JsonValidator {
	private JacksonSchema jschema ;

	public VahlasJsonValidator() {
	}

	public VahlasJsonValidator(File schemaFile) throws IOException {
		if (!schemaFile.exists()) {
			throw new IOException("Schema file "+schemaFile+" does not exist");
		}
		ObjectMapper mapper = new ObjectMapper();
		String schema = new String(Files.toByteArray(schemaFile));
		JsonNode schemaNode = mapper.readTree(schema);
		jschema = new JacksonSchema(mapper, schemaNode);
	}

	public List<String> validate(File instanceFile) throws IOException {
		if (jschema == null)
			throw new IOException("No schema");

		if (!instanceFile.exists()) {
			throw new IOException("Instance file "+instanceFile+" does not exist");
		}
		String json = new String(Files.toByteArray(instanceFile));
		return validate(json);
	}

	public List<String> validate(String json) throws IOException {
		List<String> res = jschema.validate(json);
		return res;
	}

	public JsonValidator createInstance(File schemaFile) throws IOException {
		return new VahlasJsonValidator(schemaFile);
	}
}
