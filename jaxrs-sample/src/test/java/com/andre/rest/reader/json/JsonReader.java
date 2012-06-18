package com.andre.rest.reader.json;

import com.andre.rest.reader.ObjectReader;
import java.util.*;
import java.io.*;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonReader implements ObjectReader {
	private ObjectMapper mapper = new ObjectMapper();
	private Class clazz ;

	public JsonReader(Class clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public Object read(String content) throws Exception {
		Object obj = mapper.readValue(content,clazz);
		return obj;
	}
}
