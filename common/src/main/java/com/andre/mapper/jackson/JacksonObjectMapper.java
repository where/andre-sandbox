package com.andre.mapper.jackson;

import java.util.*;
import java.io.*;
import com.andre.mapper.ObjectMapperUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.DeserializationConfig;

public class JacksonObjectMapper implements com.andre.mapper.ObjectMapper {
	private ObjectMapper mapper = new ObjectMapper();

	public JacksonObjectMapper() {
		init();
	}

	public JacksonObjectMapper(boolean prettyPrint) {
		init();
		mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,prettyPrint);
	}

	private void init() {
		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false); // NOTE: ISO 8601 but no Timezone
		mapper.getSerializationConfig().withDateFormat(ObjectMapperUtils.ISO_8601_DATE_FORMAT); // NOTE: doesn't pick up Timezone!!
		//mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false); // NOTE: doesn't ignore 'type' field
		//mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
	}

	public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		return mapper.readValue(content,clazz);
	}

	public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		return mapper.readValue(new String(content),clazz);
	}

	public String toString(Object obj) throws Exception {
		return mapper.writeValueAsString(obj);
	}

	public byte [] toBytes(Object obj) throws Exception {
		return mapper.writeValueAsBytes(obj);
	}

	public String getContentType() {
		return "application/json" ;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}	
}
