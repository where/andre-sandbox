package com.andre.mapper.gson;

import java.util.*;
import java.io.*;
import com.andre.mapper.ObjectMapper ;
import com.andre.mapper.ObjectMapperUtils ;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.text.DateFormat;

public class GsonObjectMapper implements ObjectMapper {
	private Gson gson ;
	private GsonBuilder gsonBuilder = new GsonBuilder();

	public GsonObjectMapper() {
		init() ;
	}

	public GsonObjectMapper(boolean prettyPrint) {
		if (prettyPrint)
			gsonBuilder.setPrettyPrinting();
		init() ;
	}

	private void init() {
		gsonBuilder.setDateFormat(DateFormat.LONG);
		gsonBuilder.setDateFormat(ObjectMapperUtils.ISO_8601_FORMAT);
		//gsonBuilder.setDateFormat(DateFormat.DEFAULT);
		//gsonBuilder.serializeNulls() ;
		gson = gsonBuilder.create();
	}

	public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		return gson.fromJson(content,clazz);
	}

	public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		return gson.fromJson(new InputStreamReader(new ByteArrayInputStream(content)), clazz);
	}

	public String toString(Object obj) throws Exception {
		return gson.toJson(obj);
	}

	public byte [] toBytes(Object obj) throws Exception {
		String json = gson.toJson(obj);
		return json.getBytes();
	}

	public String getContentType() {
		return "application/json" ;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}	
}
