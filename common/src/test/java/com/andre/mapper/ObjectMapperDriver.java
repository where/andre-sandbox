package com.andre.mapper;

import com.google.common.io.Files;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.andre.mapper.ObjectMapper;
import com.andre.mapper.jackson.JacksonObjectMapper;
import com.andre.mapper.gson.GsonObjectMapper;
import com.andre.mapper.jaxb.JaxbObjectMapper;
import com.andre.util.TestNgUtils;

public class ObjectMapperDriver {
	private static final Logger logger = Logger.getLogger(ObjectMapperDriver.class);
	private List<ObjectMapper> mappers = new ArrayList<ObjectMapper>();
	private boolean prettyPrint = true;

	public static void main(String [] args) throws Exception {
		(new ObjectMapperDriver()).process(args);
	}

	public void process(String [] args) throws Exception {
		init();
		processBean() ;
		processList() ;
	}

	public void processBean() throws Exception {
		Video obj = Utils.createVideo();
		obj.setMainGenre(new Genre("locura",1));
		obj.addGenre(new Genre("hiking",10));
		obj.addGenre(new Genre("mountains",20));

		for (ObjectMapper mapper : mappers) {
			String name = mapper.getClass().getSimpleName();
			String ext = getLast(mapper.getContentType());
			info("ContentType="+mapper.getContentType()+" name="+name);
			byte [] content = mapper.toBytes(obj) ;
			String fname = "video-" + name + "." + ext ;
			File file = new File(fname);
			Files.write(content,file);
		}
	}

	public void processList() throws Exception {
		List<Genre> genres = new ArrayList<Genre>();
		genres.add(new Genre("hiking",10));
		genres.add(new Genre("mountains",20));

		for (ObjectMapper mapper : mappers) {
			String name = mapper.getClass().getSimpleName();
			String ext = getLast(mapper.getContentType());
			info("ContentType="+mapper.getContentType()+" name="+name);
			byte [] content = mapper.toBytes(genres) ;
			String fname = "genres-" + name + "." + ext ;
			File file = new File(fname);
			Files.write(content,file);
		}
	}

	String getLast(String contentType) {
		String [] toks = contentType.split("/");
		return toks[toks.length-1] ;
	}

	void init() throws Exception {
		mappers.add(new JacksonObjectMapper(prettyPrint));
		mappers.add(new GsonObjectMapper(prettyPrint));
		mappers.add(new JaxbObjectMapper());
	}
	
	void error(Object o) { System.out.println("ERROR: "+o);}
	void info(Object o) { System.out.println(""+o);}
}
