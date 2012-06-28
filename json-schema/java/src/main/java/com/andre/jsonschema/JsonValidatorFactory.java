package com.andre.jsonschema;

import java.util.*;

public class JsonValidatorFactory {
	private List<JsonValidator> validators = new ArrayList<JsonValidator>();

	JsonValidatorFactory() {
	}	

	JsonValidatorFactory(List<JsonValidator> validators) {
		this.validators = validators ;
	}	

	public JsonValidator getJsonValidator(String name) {
		for (JsonValidator validator : validators) {
			if (name.equals(validators.getClass().getName()))
				return validator;
		}
		return null;
	}
}
