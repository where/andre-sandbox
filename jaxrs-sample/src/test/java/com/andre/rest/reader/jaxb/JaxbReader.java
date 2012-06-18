package com.andre.rest.reader.jaxb;

import com.andre.jaxb.JaxbUtils;
import com.andre.rest.reader.ObjectReader;
import javax.xml.bind.JAXBElement;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

// TODO: How to read JSON using JAXB?

public class JaxbReader implements ObjectReader {
	private static final Logger logger = Logger.getLogger(ObjectReader.class);
	private File schemaFile ;
	private Class clazz ;
	private String pkg ;

	public JaxbReader(File schemaFile, Class clazz, String pkg) throws Exception {
		JaxbUtils.createSchemaFile(schemaFile,pkg) ;
		this.clazz = clazz;
		this.pkg = pkg;
		this.schemaFile = schemaFile;
		//logger.debug("schemaFile="+schemaFile);
	}

	public Object read(String xml) throws Exception {
		Object obj = JaxbUtils.readXmlContent(xml,pkg,schemaFile);
		//logger.debug("obj="+obj);
		return obj ;
	}

	public Object read(File file) throws Exception {
		Object obj = JaxbUtils.readXmlFile(file,pkg,schemaFile);
		return obj ;
	}
}
