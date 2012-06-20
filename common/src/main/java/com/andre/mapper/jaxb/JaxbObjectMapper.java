package com.andre.mapper.jaxb;

import com.andre.jaxb.JaxbUtils;
import com.andre.mapper.ObjectMapper;
import javax.xml.bind.JAXBElement;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

// TODO: How to read JSON using JAXB?

public class JaxbObjectMapper implements ObjectMapper {
	private static final Logger logger = Logger.getLogger(ObjectMapper.class);
	private File schemaFile ;

	public JaxbObjectMapper(File schemaFile) throws Exception {
		this.schemaFile = schemaFile;
	}

	public Object toObject(String xml, Class clazz) throws Exception {
		Object obj = JaxbUtils.readXmlContent(xml,clazz,schemaFile);
		return obj ;
	}

	public String toString(Object obj) throws Exception {
		return JaxbUtils.toString(obj);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
