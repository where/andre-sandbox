package com.andre.mapper;

import com.google.common.io.Files;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.andre.mapper.ObjectMapper;
import com.andre.mapper.jackson.JacksonObjectMapper;
import com.andre.mapper.jackson.JaxrsJacksonJsonProviderMapper;
import com.andre.mapper.jackson2.Jackson2ObjectMapper;
import com.andre.mapper.gson.GsonObjectMapper;
import com.andre.mapper.jaxb.JaxbObjectMapper;
import com.andre.mapper.jettison.JettisonObjectMapper;
import com.andre.mapper.moxy.MoxyJaxbObjectMapper;
import javax.ws.rs.core.MediaType;

public class ObjectMapperDriver {
	private static final Logger logger = Logger.getLogger(ObjectMapperDriver.class);
	private List<ObjectMapper> mappers = new ArrayList<ObjectMapper>();
	private boolean prettyPrint = true;

	public static void main(String [] args) throws Exception {
		(new ObjectMapperDriver()).process(args);
	}

	public void process(String [] args) throws Exception {
		init(args);
		if ("object".equals(options.method)) {
			processVideo() ;
		} else if ("list".equals(options.method)) {
			processList() ;
		} else if ("all".equals(options.method)) {
			processVideo() ;
			processList() ;
		} else {
			error("Unknown method="+options.method);
		}
	}

	public void processVideo() throws Exception {
		Video obj = Utils.createVideo();
		obj.setStartDate(Utils.REF_DATE);
		obj.setMainGenre(new Genre("chaura",1));
		obj.addGenre(new Genre("hiking",10));
		obj.addGenre(new Genre("mountains",20));

		info("Object");
		int total=0;
		int errors=0;
		for (ObjectMapper mapper : mappers) {
			total++;
			String cname = mapper.getClass().getSimpleName();
			String ext = getLast(mapper.getContentType());
			try {
				byte [] content = mapper.toBytes(obj) ;
				String fname = "oo-video-" + cname + "." + ext ;
				info("  "+fname);
				File file = new File(fname);
				Files.write(content,file);

				Video obj2 = mapper.toObject(content,Video.class) ;
			} catch (Exception e) {
				errors++;
				error("Mapper "+cname+" EX="+e);
				e.printStackTrace();
			}
		}
		info("Mappers: errors="+errors+"/"+total);
	}

	public void processList() throws Exception {
		List<Genre> genres = new ArrayList<Genre>();
		genres.add(new Genre("hiking",10));
		genres.add(new Genre("mountains",20));

		info("List");
		for (ObjectMapper mapper : mappers) {
			String cname = mapper.getClass().getSimpleName();
			try {
				String ext = getLast(mapper.getContentType());
				byte [] content = mapper.toBytes(genres) ;
				String fname = "out-genres-" + cname + "." + ext ;
				//info("  ContentType="+mapper.getContentType()+" file="+fname);
				info("  "+fname);
				File file = new File(fname);
				Files.write(content,file);
			} catch (Exception e) {
				error("Mapper "+cname+" EX="+e);
			}
		}
	}

	String getLast(String contentType) {
		String [] toks = contentType.split("/");
		return toks[toks.length-1] ;
	}

	void init() throws Exception {
		mappers.add(new JacksonObjectMapper(prettyPrint));
		mappers.add(new Jackson2ObjectMapper(prettyPrint));
		mappers.add(new GsonObjectMapper(prettyPrint));
		mappers.add(new JaxbObjectMapper());
		mappers.add(new JaxrsJacksonJsonProviderMapper());
		mappers.add(new JettisonObjectMapper(prettyPrint));
		mappers.add(new MoxyJaxbObjectMapper(MediaType.APPLICATION_JSON, prettyPrint));
		mappers.add(new MoxyJaxbObjectMapper(MediaType.APPLICATION_XML, prettyPrint));
	}

	private Options options = new Options();
	private JCommander jcommander;

	private boolean init(String [] args) throws Exception {
		initOptions(args);
		init();
		return true;
	}

	private boolean initOptions(String [] args) {
		jcommander = new JCommander(options, args);
		info("Options:");
		info("  method="+options.method);
		return true;
	}

	public class Options {
		@Parameter(names = { "--method", "-m" }, description = "Method", required=true)
		public String method ;
	}
	
	void error(Object o) { System.out.println("ERROR: "+o);}
	void info(Object o) { System.out.println(o);}
}
