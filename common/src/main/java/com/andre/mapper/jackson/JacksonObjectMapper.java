package com.andre.mapper.jackson;

import java.util.*;
import java.io.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class JacksonObjectMapper implements com.andre.mapper.ObjectMapper {
	private ObjectMapper mapper = new ObjectMapper();
	private ObjectWriter writer ;

	public JacksonObjectMapper() {
		writer = mapper.writer();
	}

	public JacksonObjectMapper(boolean prettyPrint) {
		writer = prettyPrint ?  writer = mapper.writerWithDefaultPrettyPrinter() : mapper.writer();
	}

    public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		return mapper.readValue(content,clazz);
	}

    public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		return mapper.readValue(new String(content),clazz);
	}

	public String toString(Object obj) throws Exception {
		return writer.writeValueAsString(obj);
	}


    public byte [] toBytes(Object obj) throws Exception {
		return writer.writeValueAsBytes(obj);
	}

	public String getContentType() {
		return "application/json" ;
	}


	@Override
	public String toString() {
		return getClass().getSimpleName();
	}	
}
