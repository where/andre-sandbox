package com.andre.jaxb;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;

import javax.xml.bind.Marshaller;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Result;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

/**
 * JAXB utilities.
 */
public class JaxbUtils {
	private static final Logger logger = Logger.getLogger(JaxbUtils.class);

	public static String toString(Object obj) {
		StringWriter swr = new StringWriter();
		JAXB.marshal(obj, swr);
		return swr.toString();
	}

	static public Object readXmlContent(String xmlContent, String pkg, File schemaFile) throws IOException, ParsingException, Exception {
		return readXmlStream(new ByteArrayInputStream(xmlContent.getBytes()), pkg, schemaFile) ;
	}

	static public Object readXmlFile(File xmlFile, String pkg, File schemaFile) throws IOException, ParsingException, Exception {
		return readXmlStream(new FileInputStream(xmlFile), pkg, schemaFile) ;
	}


	static public Object readXmlStream(InputStream stream, String pkg, File schemaFile) throws IOException, ParsingException, Exception {
		return readXmlStream(stream, JAXBContext.newInstance(pkg), schemaFile) ;
	}

	static public Object readXmlStream(InputStream stream, Class clazz, File schemaFile) throws IOException, ParsingException, Exception {
		return readXmlStream(stream, JAXBContext.newInstance(clazz), schemaFile) ;
	}


	static public Object readXmlContent(String xmlContent, Class clazz, File schemaFile) throws IOException, ParsingException, Exception {
		return readXmlStream(new ByteArrayInputStream(xmlContent.getBytes()), clazz, schemaFile) ;
	}


	static public Object readXmlStream(InputStream stream, JAXBContext context, File schemaFile) throws IOException, ParsingException, Exception {
		//logger.debug("schemaFile="+schemaFile);
		if (stream == null)
  			throw new ParsingException("No content to parse");

		Unmarshaller unmarshaller = context.createUnmarshaller();
		if (unmarshaller == null)
  			throw new ParsingException("unmarshaller is null");
		//Schema schema = unmarshaller.getSchema();

		try {
			if (schemaFile == null) {
				return unmarshaller.unmarshal(stream);
			} else {
				CustomValidationEventHandler handler = validate(schemaFile, unmarshaller);
				Object obj = unmarshaller.unmarshal(stream);
				List<String> errors = handler.getErrors() ;
				logger.debug("#errors="+errors.size());
				if (errors.size() > 0) {
		  			throw new InvalidFormatException(errors);
				}
				return obj ;
			}
		} catch (javax.xml.bind.UnmarshalException e) {
			throw new IllegalSyntaxException(e);
		}
	}

	public static CustomValidationEventHandler validate(File schemaFile, Unmarshaller u)
		throws JAXBException
	{
		SchemaFactory schemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
		CustomValidationEventHandler handler = new CustomValidationEventHandler();
		try {
			Schema schema = schemaFactory.newSchema(schemaFile);
			u.setSchema(schema);
			u.setEventHandler(handler);
		} catch (org.xml.sax.SAXException ex) {
			logger.error("Unable to validate due to following error:");
			ex.printStackTrace();
		}
		return handler ;
	}

	/** Generate a schema from annotated JAXB classes indicated in package. */

	static public void createSchemaFile(File schemaFile, String pkg) throws IOException, Exception {
	 	JAXBContext context = JAXBContext.newInstance(pkg);
		context.generateSchema(new MySchemaOutputResolver(schemaFile));
	}

	static class MySchemaOutputResolver extends SchemaOutputResolver {
		private File schemaFile;

		MySchemaOutputResolver(File schemaFile) {
			this.schemaFile = schemaFile;
		}

		public Result createOutput(String namespaceUri, String _ignoreMe_SchemaFile) throws IOException {
			return new StreamResult(schemaFile);
		}
	}
}

