package com.andre.mapper.javaser;

import java.util.*;
import java.io.*;
import com.andre.util.ObjectMapperUtils;
import com.andre.mapper.ObjectMapper;

public class JavaSerObjectMapper implements ObjectMapper {

	@SuppressWarnings("unchecked")
    public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		return (T)ObjectMapperUtils.objectFromBytes(content.getBytes());
	}

	@SuppressWarnings("unchecked")
    public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		return (T)ObjectMapperUtils.objectFromBytes(content);
	}

	public String toString(Object obj) throws Exception {
		return new String(ObjectMapperUtils.objectToBytes(obj));
	}

    public byte [] toBytes(Object obj) throws Exception {
		return ObjectMapperUtils.objectToBytes(obj);
	}

	public String getContentType() {
		return "application/x-java-serialized-object";
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}	
}
