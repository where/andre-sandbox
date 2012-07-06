package com.andre.mapper.gson;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonObjectMapper implements com.andre.mapper.ObjectMapper {
    private Gson gson ;

	public GsonObjectMapper() {
    	gson = new Gson();
	}

	public GsonObjectMapper(boolean prettyPrint) {
    	gson = prettyPrint ?  new GsonBuilder().setPrettyPrinting().create() : new Gson();
	}

	public <T> T toObject(String content, Class<T> clazz) throws Exception  {
        return gson.fromJson(content,clazz);
	}

    public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
        String json = new String(content);
        return gson.fromJson(json,clazz);
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
