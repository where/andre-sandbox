package com.andre.jsonschema.bean;

import com.google.common.io.Files;
import org.apache.commons.lang.WordUtils;
import com.andre.mapper.ObjectMapper;
import com.andre.mapper.jackson.*;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.andre.jsonschema.*;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class BeanGenerator {
	private static final Logger logger = Logger.getLogger(BeanGenerator.class);
	private ObjectMapper objectMapper = new JsonObjectMapper();
	private static final String QUOTE = ";" ;
	private static final String NL = "\n" ;
	private Map<String,String> typeMap = new HashMap<String,String> ();

	public static void main(String [] args) throws Exception {
		(new BeanGenerator()).process(args);
	}

	public BeanGenerator() {
		typeMap.put("string","String");
		typeMap.put("number","Double");
		typeMap.put("integer","Integer");
	}

	public void process(String [] args) throws Exception {
		init(args);
		process(new File(options.schemaFilename));
	}

	public void process(File schemaFile) throws Exception {
		String schemaString = new String(Files.toByteArray(schemaFile));
		Schema schema = (Schema)objectMapper.toObject(schemaString, Schema.class) ;

		String outputFilename = options.className+".java" ;
		PrintWriter writer = new PrintWriter(new FileWriter(outputFilename));

		writer.println("package "+options.pkg+QUOTE);
		writer.println();
		writer.println("public class "+options.className+" {");
		writer.println();

		for (Map.Entry<String,Object> entry : schema.getProperties().entrySet() ) {
			buildProp(writer, entry.getKey(), entry.getValue());
		}

		writer.println("}");
		writer.close();
	}

	void buildProp(PrintWriter writer, String name, Object tobj) throws IOException {
		String tab = options.tab ;
		String tab2 = options.tab + options.tab;
		Map<String,Object> tmap = (Map<String,Object>)tobj;
		String jsonType = (String)tmap.get("type");
		String javaType = typeMap.get(jsonType);
		String uname = WordUtils.capitalize(name);
		if (javaType == null) {
			//throw IllegalArgumentException("Can't find Java type for JSON type "+jsonType);
			jsonType = javaType ;
		}
		String title = (String)tmap.get("title");
		
		writer.println(options.tab+"private "+javaType+" "+name+QUOTE);	

		if (null != title) writeComment(writer,"Gets "+title);
		writer.println(options.tab+"public "+javaType+" get"+uname+"() { "+NL+tab2+"return "+name+QUOTE+NL+tab+"}");

		if (null != title) writeComment(writer,"Sets "+title);
		writer.println(options.tab+"public void set"+uname+" ("+javaType+" "+name+") {"+NL+tab2+"this."+name+" = "+name+QUOTE+NL+tab+"}");

/*
		writer.println(options.tab+"private "+javaType+" "+name+QUOTE);	
		writer.println(options.tab+"public "+javaType+" get"+uname+"() { return "+name+QUOTE+" }");
		writer.println(options.tab+"public void set"+uname+" ("+javaType+" "+name+") { this."+name+" = "+name+QUOTE+" }");
*/
		writer.println();
	}

	void writeComment(PrintWriter writer, String msg) throws IOException {
		writer.println(options.tab+"/** "+msg+". */");
	}


	private Options options = new Options();
	private JCommander jcommander;

	private boolean init(String [] args) throws Exception {
		return initOptions(args);
	}

	private boolean initOptions(String [] args) {
		jcommander = new JCommander(options, args);
		info("Options:");
		info("  schemaFilename="+options.schemaFilename);
		info("  className="+options.className);
		info("  package="+options.pkg);
		return true;
	}

	public class Options {
		@Parameter(names = { "--schemaFile", "-s" }, description = "Schema file", required=true)
		public String schemaFilename ;

		@Parameter(names = { "--classFile", "-c" }, description = "Bean class name", required=true)
		public File className ;

		@Parameter(names = { "--package", "-p" }, description = "Java package", required=true)
		public String pkg ;

		@Parameter(names = { "--tab", "-t" }, description = "Tab", required=false)
		public String tab = "\t" ;
	}
	
	void error(Object o) { System.out.println("ERROR: "+o);}
	void info(Object o) { System.out.println(o); }
}
