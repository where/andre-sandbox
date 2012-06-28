package com.andre.jsonschema;

import java.util.*;
import java.io.*;

/**
 * High level JSON validator service.
 */
public interface JsonValidator {
	public List<String> validate(File instanceFile) throws Exception ;
	public List<String> validate(String json) throws Exception ;
	public JsonValidator createInstance(File schemaFile) throws IOException ;
}
