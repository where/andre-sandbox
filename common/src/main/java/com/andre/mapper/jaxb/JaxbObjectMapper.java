package com.andre.mapper.jaxb;

import com.andre.jaxb.JaxbUtils;
import com.andre.mapper.ObjectMapper;
import javax.xml.bind.JAXBElement;
import java.util.*;
import java.io.*;

public class JaxbObjectMapper implements ObjectMapper {
	private File schemaFile ;

	public JaxbObjectMapper() throws Exception {
	}

	public JaxbObjectMapper(File schemaFile) throws Exception {
		this.schemaFile = schemaFile;
	}

	public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		return JaxbUtils.readXmlContent(content,clazz,schemaFile); 
	}

	public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		return JaxbUtils.readXmlContent(new String(content),clazz,schemaFile);
	}

	public String toString(Object obj) throws Exception {
		return JaxbUtils.toString(obj);
	}

	public byte [] toBytes(Object obj) throws Exception {
		return JaxbUtils.toString(obj).getBytes();
	}

	public String getContentType() {
		return "application/xml" ;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
