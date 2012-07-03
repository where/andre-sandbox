package com.andre.mapper.jackson;

import java.util.*;
import java.io.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

/*
    ObjectMapper mapper = new ObjectMapper();
    MyClass myObject = mapper.readValue(new FileReader("input.json"), MyClass.class);
    ObjectWriter writer = mapper.defaultPrettyPrintingWriter();
    System.out.println(writer.writeValueAsString(myObject));
*/

public class JsonObjectMapper implements com.andre.mapper.ObjectMapper {
	private ObjectMapper mapper ;
	private ObjectWriter writer ;

	public JsonObjectMapper() {
		mapper = new ObjectMapper();
		writer = mapper.writer();
	}

	public JsonObjectMapper(boolean prettyPrint) {
		mapper = new ObjectMapper();
		writer = prettyPrint ?  writer = mapper.writerWithDefaultPrettyPrinter() : mapper.writer();
	}

	@SuppressWarnings("unchecked")
	public Object toObject(String content, Class clazz) throws Exception {
		return mapper.readValue(content,clazz);
	}

    @SuppressWarnings("unchecked")
    public Object toObject(byte [] content, Class clazz) throws Exception  {
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
