package com.andre.pphere.rest;

import java.util.*;
import java.io.*;
import com.andre.pphere.data.Store;

public class ContentFactory {
	//public static final String JAVA_OBJECT = "application/x-java-serialized-object" ;

	public static byte [] toBytes(Store store, String contentType) {
		return getStore(contentType) ;
	}

	public static byte [] getStore(String contentType) {
		if ("json".equals(contentType))
			return STORE_JSON.getBytes() ;
		else if ("xml".equals(contentType))
			return STORE_XML.getBytes() ;
		//else if (JAVA_OBJECT.equals(contentType))
			//return ObjectMapperUtils.objectToBytes(STORE);
		else
			return STORE_JSON.getBytes() ;
	}

	private static String STORE_XML = 
		  "<store >"
		+ "	<name>TestNG XML</name>"
		+ "	<id>990</id>"
		+ "</store>"
		;

	private static String STORE_JSON_DEFAULT = 
		  "{\"store\":{"
		+ "  \"name\":\"TestNG JSON Default\","
		+ "  \"description\":\"Default JSON \","
		+ "  \"id\":888"
		+ "}}" ;
	private static String STORE_JSON_JACKSON = 
		  "{"
		+ "  \"name\":\"TestNG JSON Jackson\","
		+ "  \"id\":888,"
		+ "  \"description\":\"Jackson JSON\" "
		+ "}" ;

	//private static String STORE_JSON = STORE_JSON_DEFAULT ;
	private static String STORE_JSON = STORE_JSON_JACKSON ;
}
