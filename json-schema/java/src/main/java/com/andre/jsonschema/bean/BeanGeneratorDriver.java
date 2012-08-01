package com.andre.jsonschema.bean;

import com.google.common.io.Files;
import org.apache.commons.lang.WordUtils;
import com.andre.mapper.ObjectMapper;
import com.andre.mapper.jackson.JacksonObjectMapper;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.andre.jsonschema.*;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

/**
 * Bean generator CLI.
 */
public class BeanGeneratorDriver {
	private static final Logger logger = Logger.getLogger(BeanGeneratorDriver.class);
	private ObjectMapper objectMapper = new JacksonObjectMapper();
	private static final String QUOTE = ";" ;
	private static final String NL = "\n" ;
	private Map<String,String> typeMap = new HashMap<String,String> ();
	private Map<String,String> formatMap = new HashMap<String,String> ();

	public static void main(String [] args) throws Exception {
		(new BeanGeneratorDriver()).process(args);
	}

	public void process(String [] args) throws Exception {
		init(args);
		BeanGenerator generator = new BeanGenerator();
		generator.process(options.schemaFile, options.className, options.pkg, options.jaxb);
	}

    private Options options = new Options();
    private JCommander jcommander;

    private boolean init(String [] args) throws Exception {
        return initOptions(args);
    }

    private boolean initOptions(String [] args) {
        jcommander = new JCommander(options, args);
        info("Options:");
        info("  schemaFile="+options.schemaFile);
        info("  className="+options.className);
        info("  package="+options.pkg);
        info("  jaxb="+options.jaxb);
        return true;
    }

	public class Options {
		@Parameter(names = { "--schemaFile", "-s" }, description = "Schema file", required=true)
		public File schemaFile ;

		@Parameter(names = { "--classFile", "-c" }, description = "Bean class name", required=true)
		public File className ;

		@Parameter(names = { "--package", "-p" }, description = "Java package", required=true)
		public String pkg ;

		@Parameter(names = { "--jaxb", "-j" }, description = "insert JAXB annotations", required=false)
		public boolean jaxb ;

		@Parameter(names = { "--tab", "-t" }, description = "Tab", required=false)
		public String tab = "\t" ;
	}

	void info(Object o) { System.out.println(o); }
}
