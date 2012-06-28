package com.andre.jsonschema;

import java.util.*;

public class TestConfig {
 
	private String baseDir; 
	public String getBaseDir() { return baseDir; }
	public void setBaseDir(String baseDir) { this.baseDir = baseDir; } 

	private String schemaFile; 
	public String getSchemaFile() { return schemaFile; }
	public void setSchemaFile(String schemaFile) { this.schemaFile = schemaFile; } 
 
	private String schemaFileRef; 
	public String getSchemaFileRef() { return schemaFileRef; }
	public void setSchemaFileRef(String schemaFileRef) { this.schemaFileRef = schemaFileRef; } 

	@Override
	public String toString() {
		return
			  "schemaFile=" + schemaFile 
			+ " baseDir=" + baseDir 
			+ " schemaFileRef=" + schemaFileRef 
		;
	}
}
