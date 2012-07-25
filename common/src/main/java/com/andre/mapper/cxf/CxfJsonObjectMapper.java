package com.andre.mapper.cxf;

import java.util.*;
import java.io.*;
import com.andre.mapper.ObjectMapper;
//import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MediaType;

public class CxfJsonObjectMapper implements com.andre.mapper.ObjectMapper {
	private JSONProvider provider = new JSONProvider();
	//private Map<Class<?>,JSONProvider<?>> cache = new HashMap<Class<?>,JSONProvider<?>> ();
	private Map<Class<?>,JSONProvider<?>> cache = new HashMap<Class<?>,JSONProvider<?>> ();

	public CxfJsonObjectMapper() {
	}

	public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		return toObject(content.getBytes(),clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		Object obj = provider.readFrom(clazz, null, null, null, null, new ByteArrayInputStream(content));
		//Object obj = doit(content,clazz);
		return (T)obj;
	}
	
	@SuppressWarnings("unchecked")
	Object doit(byte [] content, Class clazz) throws IOException {
		//Class clazz = String.class ;
		Object obj = provider.readFrom(clazz, null, null, null, null, new ByteArrayInputStream(content));
		return obj ;
	}

	public String toString(Object obj) throws Exception {
		ByteArrayOutputStream os = _toBytes(obj);
		return os.toString();
	}

	public byte [] toBytes(Object obj) throws Exception {
		ByteArrayOutputStream os = _toBytes(obj);
		return os.toByteArray();
	}

	@SuppressWarnings("unchecked")
	private ByteArrayOutputStream _toBytes(Object obj) throws Exception {
		Class clazz = obj.getClass();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
		MultivaluedMap<String,Object> mmap = null;
		provider.writeTo(obj, clazz, clazz, clazz.getAnnotations(), mediaType, mmap, os);
		return os;
	}

/*
	private <T> JSONProvider<T> getProvider(Class<T> clazz) {
		JSONProvider<T> provider = cache.get(clazz);
		if (provider == null) {
			provider = new JSONProvider<T>();
			cache.put(clazz,provider);
		}
		return null;
	}
*/

	public String getContentType() {
		return "application/json" ;
	}


	@Override
	public String toString() {
		return getClass().getSimpleName();
	}	
}
