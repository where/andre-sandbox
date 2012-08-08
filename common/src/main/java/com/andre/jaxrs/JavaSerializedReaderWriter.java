package com.andre.jaxrs;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import java.lang.annotation.Annotation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

/**
 * Provider for Java serialized object format.
 */
@Provider
@Consumes("application/x-java-serialized-object")
@Produces("application/x-java-serialized-object")
public class JavaSerializedReaderWriter implements MessageBodyReader<Object>, MessageBodyWriter<Object> {
	private static final Logger logger = Logger.getLogger(JavaSerializedReaderWriter.class);

	public boolean isReadable(java.lang.Class<?> type, 
			java.lang.reflect.Type genericType, java.lang.annotation.Annotation[] 
			annotations, MediaType mediaType) {
		return Serializable.class.isAssignableFrom(type);
	}
	
	public Object readFrom(java.lang.Class<Object> type, 
			java.lang.reflect.Type genericType, java.lang.annotation.Annotation[] 
			annotations, MediaType mediaType, MultivaluedMap<
			java.lang.String,java.lang.String> httpHeaders, java.io.InputStream is) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(is);
		try {
			return ois.readObject();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isWriteable(java.lang.Class<?> type, 
			java.lang.reflect.Type genericType, java.lang.annotation.Annotation[] 
			annotations, MediaType mediaType) {
		return Serializable.class.isAssignableFrom(type);
	}

	public void writeTo(Object obj, Class<?> cls, java.lang.reflect.Type genericType, Annotation[] anns,
						MediaType m, MultivaluedMap<String, Object> headers, OutputStream os)
						throws IOException {
		logger.debug("obj="+obj);
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeObject(obj);
	}

 	public long	getSize(Object t, java.lang.Class<?> type, java.lang.reflect.Type genericType, java.lang.annotation.Annotation[] annotations, MediaType mediaType) {
		return -1;
	}
}
