package com.andre.jsonschema.bean;

import java.util.*;

public class Schema {

	private Object type; 
	public Object getType() { return type; }
	public void setType(Object type) { this.type = type; } 
 
	private String description; 
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; } 
 
	private Map<String,Object> properties = new LinkedHashMap<String,Object>();
	public Map<String,Object> getProperties() { return properties; }
	public void setProperties(Map<String,Object> properties) { this.properties = properties; } 
 
	private boolean additionalProperties; 
	public boolean getAdditionalProperties() { return additionalProperties; }
	public void setAdditionalProperties(boolean additionalProperties) { this.additionalProperties = additionalProperties; } 

	@Override
	public String toString() {
		return
			  "description=" + description 
			+ " type=" + type 
			+ " additionalProperties=" + additionalProperties 
			+ " #properties=" + properties.size()
		;
	}
}
