package com.andre.jsonschema.bean;

import com.google.common.io.Files;
import org.apache.commons.lang.WordUtils;
import com.andre.mapper.ObjectMapper;
import com.andre.mapper.jackson.JacksonObjectMapper;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.andre.jsonschema.*;

/**
 * Generates a Java bean from a JSON schema.
 */
public class BeanGenerator {
	private static final Logger logger = Logger.getLogger(BeanGenerator.class);
	private ObjectMapper objectMapper = new JacksonObjectMapper();
	private static final String SEMICOLON = ";" ;
	private static final String NL = "\n" ;
	private Map<String,String> typeMap = new HashMap<String,String> ();
	private Map<String,String> formatMap = new HashMap<String,String> ();
	private Map<String,String> valueMap = new HashMap<String,String> ();
	private String TAB = "\t" ;
	private boolean jaxb ;
	private boolean writeValue = true ;

	public BeanGenerator() {

		typeMap.put("string","String");
		typeMap.put("number","Double");
		typeMap.put("integer","Integer");
		typeMap.put("boolean","boolean");
		//typeMap.put("array","?");

		formatMap.put("date-time","Date");

		valueMap.put("String","\"july\""); // TODO ?
		valueMap.put("Integer","2012");
		valueMap.put("int","2012");
		valueMap.put("boolean","false");
		valueMap.put("Boolean","false");
		valueMap.put("Long","2013L");
		valueMap.put("long","2013L");
		valueMap.put("double","88.6");
		valueMap.put("Double","88.6");
		valueMap.put("float","78.6");
		valueMap.put("Float","78.6");
		valueMap.put("Date","new java.util.Date()");
	}

	public void process(File schemaFile, File className, String pkg, boolean jaxb) throws Exception { 
		this.jaxb = jaxb ;

		String schemaString = new String(Files.toByteArray(schemaFile));
		Schema schema = (Schema)objectMapper.toObject(schemaString, Schema.class) ;

		String outputFilename = className+".java" ;
		PrintWriter writer = new PrintWriter(new FileWriter(outputFilename));

		writer.println("package "+pkg+SEMICOLON);
		if (jaxb) writer.println(IMPORTS);
		writer.println();
		String cname = ""+className;
		if (jaxb) 
			writer.println(writeJaxbType(cname));
		writer.println("public class "+cname+" {");
		writer.println();

		List<String> javaProps = new ArrayList<String> ();

		for (Map.Entry<String,Object> entry : schema.getProperties().entrySet() ) {
			javaProps.add(buildProp(writer, entry.getKey(), entry.getValue()));
		}
		writeToString(writer, javaProps) ;

		writer.println("}");
		writer.close();
	}

	String buildProp(PrintWriter writer, String name, Object tobj) throws IOException {
		String uname = WordUtils.capitalize(name);
		String tab = TAB ;
		String tab2 = TAB + TAB ;
		Map<String,Object> tmap = (Map<String,Object>)tobj;
		String jsonType = (String)tmap.get("type");
		Boolean required = (Boolean)tmap.get("required");
		required = required == null ? false : true;
		String javaType ;
		if (jsonType == null) {
			String ref = (String)tmap.get("$ref");
			logger.debug("ref="+ref);
			if (ref == null) 
				throw new IllegalArgumentException("No type or ref specified");
			javaType = getClassName(ref);
		} else {
			String jsonFormat = (String)tmap.get("format");
			javaType = getJavaType(jsonType, jsonFormat) ;
			if (javaType == null) {
				//throw new IllegalArgumentException("Can't find Java type for JSON type "+jsonType);
				warning("Cannot find Java type for JSON type "+jsonType+". Using JSON type: "+jsonType);
				javaType = jsonType ;
				}
		}
		String title = (String)tmap.get("title");
		
		// Writer field
		String value = writeValue ? (" = " + getSampleValue(javaType) ): "" ;
		writer.println(TAB+"private "+javaType+" "+name+value+SEMICOLON);	

		// Writer getter
		if (null != title) writeComment(writer,"Gets "+title);
		if (jaxb) writer.println(TAB+writeJaxbElemenet(required));
		writer.println(TAB+"public "+javaType+" get"+uname+"() { "+NL+tab2+"return "+name+SEMICOLON+NL+tab+"}");

		// Writer setter
		if (null != title) writeComment(writer,"Sets "+title);
		writer.println(TAB+"public void set"+uname+" ("+javaType+" "+name+") {"+NL+tab2+"this."+name+" = "+name+SEMICOLON+NL+tab+"}");

/*
		writer.println(TAB+"private "+javaType+" "+name+SEMICOLON);	
		writer.println(TAB+"public "+javaType+" get"+uname+"() { return "+name+SEMICOLON+" }");
		writer.println(TAB+"public void set"+uname+" ("+javaType+" "+name+") { this."+name+" = "+name+SEMICOLON+" }");
*/

		writer.println();
		return name ;
	}

	private String getJavaType(String jsonType, String jsonFormat) {
		String javaType = typeMap.get(jsonType);
		if (jsonFormat == null)
			return javaType ;
		String javaType2 = formatMap.get(jsonFormat);
		//logger.debug("jsonType="+jsonType+" jsonFormat="+jsonFormat+" javaType2="+javaType2);
		if (javaType2 != null) 
			return javaType2;
		return javaType ;
	}

	void writeComment(PrintWriter writer, String msg) throws IOException {
		writer.println(TAB+"/** "+msg+". */");
	}

	void writeToString(PrintWriter writer, List<String> javaProps) throws IOException {
		String tab2 = TAB + TAB;
		String tab3 = TAB + tab2;
		writer.println(TAB+"public String toString() {");
		writer.println(tab2+"return ");
		int j=0;
		for (String name : javaProps) {
			String uname = WordUtils.capitalize(name);
			String sep = j==0 ? " " : "+" ;
			String sep2 = j==0 ? "" : " " ;
			writer.println(tab3+sep+"\""+sep2+name+"=\""+"+"+name);
			j++;
		}
		writer.println(tab3+";");
		writer.println(TAB+"}");
	}

	String getClassName(String ref) {
		String [] toks = ref.split("\\.");
		if (toks.length == 0)
			throw new IllegalArgumentException("Expecting . delimited at least two tokens");
		return toks[0];
	}

  	private static final String IMPORTS = 
  	  "import javax.xml.bind.annotation.XmlRootElement;" + NL
  	+ "import javax.xml.bind.annotation.XmlElement;" + NL
  	+ "import javax.xml.bind.annotation.XmlAccessType;" + NL
  	+ "import javax.xml.bind.annotation.XmlAccessorType;" + NL
  	+ "import javax.xml.bind.annotation.XmlType;" + NL
  	;

  	private String writeJaxbType(String type) {
		String ltype = WordUtils.uncapitalize(type);
		return 
  	  	"@XmlAccessorType(XmlAccessType.NONE)" + NL
  		+ "@XmlRootElement(name = \""+ltype+"\")" + NL
  		+ "@XmlType(name = \""+type+"\", propOrder = {" + NL
  		+ "})" + NL
  		;
	}

	String writeJaxbElemenet(boolean val) {
		return "@XmlElement(required="+val+")" ;
	}

	String getSampleValue(String javaType) {
		String value = valueMap.get(javaType);
		//if (value == null) value = "UNKNOWN_JAVA_VALUE-for-"+javaType ;
		//if (value == null) value = "null" ;
		if (value == null) value = "new "+javaType+"()" ;
		return value;
	}
	
	void warning(Object o) { System.out.println("WARNING: "+o);}
}
