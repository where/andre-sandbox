package com.andre.mapper.jackson;

import java.util.*;
import java.io.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MediaType;

// TODO: Crashes when reading, i.e. toObject

public class JaxrsJacksonJsonProviderMapper implements com.andre.mapper.ObjectMapper {
	private JacksonJsonProvider provider ;

	public JaxrsJacksonJsonProviderMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		provider = new JacksonJsonProvider(mapper);
	}

	public JaxrsJacksonJsonProviderMapper(ObjectMapper mapper) {
		provider = new JacksonJsonProvider(mapper);
	}

	public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		return toObject(content.getBytes(),clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		//Object obj = provider.readFrom(clazz, null, null, null, null, new ByteArrayInputStream(content));

		Object obj = doit(content,clazz);
		return (T)obj;
	}

	@SuppressWarnings("unchecked")
	Object doit(byte [] content, Class clazz) throws IOException {
		Object obj = provider.readFrom(clazz, null, null, null, null, new ByteArrayInputStream(content));
		return obj ;
	}
/*
	<T> void doit(byte [] content, Class<T> clazz) {
		T obj = provider.readFrom(clazz, null, null,
									   null, null, new ByteArrayInputStream(content));
	}
*/

	public String toString(Object obj) throws Exception {
		ByteArrayOutputStream os = _toBytes(obj);
		return os.toString();
	}

	public byte [] toBytes(Object obj) throws Exception {
		ByteArrayOutputStream os = _toBytes(obj);
		return os.toByteArray();
	}

	private ByteArrayOutputStream _toBytes(Object obj) throws Exception {
		Class clazz = obj.getClass();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		MediaType mediaType = MediaType.APPLICATION_JSON_TYPE;
		MultivaluedMap<String,Object> mmap = null;
		provider.writeTo(obj, clazz, clazz, clazz.getAnnotations(), mediaType, mmap, os);
		return os;
	}

/*
		JacksonJsonProvider prov = new JacksonJsonProvider();
		Object bean = new Integer[] { 1, 2, 3 };

		// First: no JSONP wrapping:
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		prov.writeTo(bean, bean.getClass(), bean.getClass(), new Annotation[0], MediaType.APPLICATION_JSON_TYPE, null, out);
		assertEquals("[1,2,3]", out.toString("UTF-8"));

*/


	public String getContentType() {
		return "application/json" ;
	}


	@Override
	public String toString() {
		return getClass().getSimpleName();
	}	
}
