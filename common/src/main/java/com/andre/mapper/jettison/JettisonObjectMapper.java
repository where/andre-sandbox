package com.andre.mapper.jettison;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.andre.mapper.ObjectMapper ;
import com.andre.mapper.ObjectMapperUtils ;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLStreamReader;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.json.JSONObject;

// http://blog.bdoughan.com/2011/04/jaxb-and-json-via-jettison-namespace.html

public class JettisonObjectMapper implements ObjectMapper {
	private static final Logger logger = Logger.getLogger(JettisonObjectMapper.class);
	private Map<Class, JAXBContext> cache = new HashMap<Class, JAXBContext>();
	private boolean prettyPrint ;

	public JettisonObjectMapper() {
		init() ;
	}

	public JettisonObjectMapper(boolean prettyPrint) {
		this.prettyPrint = prettyPrint ;
		init() ;
	}

	private void init() {
	}

	public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		return toObject(new String(content),clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		JAXBContext context = cache.get(clazz);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		JSONObject jobj = new JSONObject(content);
		Configuration config = new Configuration();
		MappedNamespaceConvention convention = new MappedNamespaceConvention(config);
		XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(jobj, convention);
		Object obj = unmarshaller.unmarshal(xmlStreamReader);
		return (T)obj;
	}

	public String toString(Object obj) throws Exception {
		return new String(toBytes(obj));
	}

	@SuppressWarnings("unchecked")
	public byte [] toBytes(Object obj) throws Exception {
		Class clazz = obj.getClass();
		JAXBContext context = getContext(clazz);

		Configuration config = new Configuration();
		MappedNamespaceConvention convention = new MappedNamespaceConvention(config);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);

		XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(convention, writer);
		Marshaller marshaller = context.createMarshaller();
		if (prettyPrint)
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // NOTE: doesn't format :(
		marshaller.marshal(obj, xmlStreamWriter);

		return out.toByteArray();
	}

	public String getContentType() {
		return "application/json" ;
	}

	private <T> JAXBContext getContext(Class<T> clazz) throws Exception {
		JAXBContext context = cache.get(clazz);
		if (context == null) {
			context = JAXBContext.newInstance(clazz);
			cache.put(clazz,context);
		}
		return context ;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}	
}
