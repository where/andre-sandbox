package com.andre.mapper.moxy;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.andre.mapper.ObjectMapper ;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import javax.ws.rs.core.MediaType;
import org.eclipse.persistence.jaxb.JAXBContextFactory;

// Cobbled from: org/eclipse/persistence/jaxb/rs/MOXyJsonProvider.java

public class MoxyJaxbObjectMapper implements ObjectMapper {
	private static final Logger logger = Logger.getLogger(MoxyJaxbObjectMapper.class);
	private Map<Class, JAXBContext> cache = new HashMap<Class, JAXBContext>();
	private boolean prettyPrint ;
	private boolean includeRoot = true; // NOTE: For JSON must be true for unmarshaller to work
	private String valueWrapper;
	private String attributePrefix = null;
	private String mediaType = MediaType.APPLICATION_JSON ;

	public MoxyJaxbObjectMapper(String mediaType) {
		this.mediaType = mediaType ;
		init() ;
	}

	public MoxyJaxbObjectMapper(String mediaType, boolean prettyPrint) {
		this.prettyPrint = prettyPrint ;
		this.mediaType = mediaType ;
		init() ;
	}

	private void init() {
	}

	@SuppressWarnings("unchecked")
	public <T> T toObject(byte [] content, Class<T> clazz) throws Exception  {
		JAXBContext context = cache.get(clazz);
		Unmarshaller unmarshaller = createUnmarshaller(context);

		ByteArrayInputStream stream = new ByteArrayInputStream(content);
		Object obj = unmarshaller.unmarshal(stream);

		return (T)obj;
	}

	public <T> T toObject(String content, Class<T> clazz) throws Exception  {
		return toObject(content.getBytes(),clazz);
	}


	@SuppressWarnings("unchecked")
	public byte [] toBytes(Object obj) throws Exception {
		Class clazz = obj.getClass();
		JAXBContext context = getContext(clazz);
		Marshaller marshaller = createMarshaller(context);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(out);
		marshaller.marshal(obj, writer);

		return out.toByteArray();
	}

	public String toString(Object obj) throws Exception {
		return new String(toBytes(obj));
	}

	public String getContentType() {
		return mediaType;
	}

	private <T> JAXBContext getContext(Class<T> clazz) throws Exception {
		JAXBContext context = cache.get(clazz);
		if (context == null) {
			context = JAXBContextFactory.createContext(new Class[] {clazz}, null);
			cache.put(clazz,context);
		}
		return context ;
	}

	private Marshaller createMarshaller(JAXBContext jaxbContext) throws PropertyException, JAXBException {
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, prettyPrint);
		marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, mediaType);

		marshaller.setProperty(MarshallerProperties.JSON_VALUE_WRAPPER, valueWrapper);
		marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
		marshaller.setProperty(MarshallerProperties.JSON_ATTRIBUTE_PREFIX, attributePrefix);

		//marshaller.setProperty(MarshallerProperties.JSON_MARSHAL_EMPTY_COLLECTIONS, marshalEmptyCollections);
		//marshaller.setProperty(MarshallerProperties.JSON_NAMESPACE_SEPARATOR, namespaceSeperator);
		//marshaller.setProperty(MarshallerProperties.NAMESPACE_PREFIX_MAPPER, namespacePrefixMapper);
		return marshaller ;
	}

	private Unmarshaller createUnmarshaller(JAXBContext jaxbContext) throws PropertyException, JAXBException {
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, mediaType);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_ATTRIBUTE_PREFIX, attributePrefix);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, includeRoot);
		//unmarshaller.setProperty(UnmarshallerProperties.JSON_NAMESPACE_PREFIX_MAPPER, namespacePrefixMapper);
		//unmarshaller.setProperty(UnmarshallerProperties.JSON_NAMESPACE_SEPARATOR, namespaceSeperator);
		unmarshaller.setProperty(UnmarshallerProperties.JSON_VALUE_WRAPPER, valueWrapper);
		return unmarshaller ;
	}
// XX

	JAXBContext getJAXBContext(Class<?> clazz) throws JAXBException {
		JAXBContext jaxbContext = JAXBContextFactory.createContext(new Class[] {clazz}, null);
		return jaxbContext;
/*
		ContextResolver<JAXBContext> resolver = providers.getContextResolver(JAXBContext.class, mediaType);
		JAXBContext jaxbContext;
		if(null == resolver || null == (jaxbContext = resolver.getContext(domainClass))) {
			return JAXBContextFactory.createContext(new Class[] {domainClass}, null);
		} else if (jaxbContext instanceof org.eclipse.persistence.jaxb.JAXBContext) {
			return jaxbContext;
		} else {
			return JAXBContextFactory.createContext(new Class[] {domainClass}, null);
		}
*/
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "-"+mediaType;
	}	
}
