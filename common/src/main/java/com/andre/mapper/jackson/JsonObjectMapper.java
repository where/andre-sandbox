package com.andre.mapper.jackson;

import java.util.*;
import java.io.*;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonObjectMapper implements com.andre.mapper.ObjectMapper {
	private ObjectMapper mapper = new ObjectMapper();

	@SuppressWarnings("unchecked")
	public Object toObject(String content, Class clazz) throws Exception {
		Object obj = mapper.readValue(content,clazz);
		return obj;
	}

	public String toString(Object obj) throws Exception {
		String value = mapper.writeValueAsString(obj);
		return value;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}	
}
