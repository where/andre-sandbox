package com.andre.mapper.jackson2;

import java.util.*;
import java.io.*;
import com.andre.mapper.ObjectMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Jackson2ObjectMapper implements com.andre.mapper.ObjectMapper {
	private ObjectMapper mapper = new ObjectMapper();

	public Jackson2ObjectMapper() {
		init();
	}

	public Jackson2ObjectMapper(boolean prettyPrint) {
		init();
		if (prettyPrint)
			mapper.enable(SerializationFeature.INDENT_OUTPUT  );
	}

	private void init() {
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS ); // NOTE: ISO 8601 but no Timezone
		//mapper.getSerializationConfig().setDateFormat(ObjectMapperUtils.ISO_8601_FORMAT);

		//mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		//mapper.enable(JsonSerialize.Inclusion.NON_NULL);
		//mapper.disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS );
		//mapper.enable(SerializationFeature.WRAP_ROOT_VALUE );
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
