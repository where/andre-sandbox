package com.andre.mapper;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.mapper.jackson.JacksonObjectMapper;
import com.andre.mapper.jackson2.Jackson2ObjectMapper;
import com.andre.mapper.jackson.JaxrsJacksonJsonProviderMapper;
import com.andre.mapper.gson.GsonObjectMapper;
import com.andre.mapper.javaser.JavaSerObjectMapper;
import com.andre.mapper.jaxb.JaxbObjectMapper;
import com.andre.mapper.cxf.CxfJsonObjectMapper;

import com.andre.util.TestNgUtils;

// test_toString_toObject - JaxrsJacksonJsonObjectMapper - bombs with NPE

public class ObjectMapperTest {
	private static final Logger logger = Logger.getLogger(ObjectMapperTest.class);
	private List<ObjectMapper> mappers = new ArrayList<ObjectMapper>();

	@DataProvider(name = "mappers")
	public Object[][] mappers() {
		return TestNgUtils.createObjectArray(mappers) ;
	}

	@BeforeClass
	void beforeClass() throws Exception {
		mappers.add(new GsonObjectMapper());
		mappers.add(new JacksonObjectMapper());
		mappers.add(new Jackson2ObjectMapper());
		mappers.add(new JaxbObjectMapper());
		mappers.add(new JavaSerObjectMapper());

		mappers.add(new JaxrsJacksonJsonProviderMapper());
		mappers.add(new CxfJsonObjectMapper());
	}

	@Test(dataProvider = "mappers")
	public void test_toBytes_toObject(ObjectMapper mapper) throws Exception {
		logger.debug("mapper="+mapper.getClass().getName());
		Video obj1 = Utils.createVideo() ;
		obj1.setStartDate(Utils.REF_DATE);

		byte [] content = mapper.toBytes(obj1) ;
		Video obj2 = mapper.toObject(content,Video.class) ;
		assertEquals(obj1,obj2);
	}

	@Test(dataProvider = "mappers")
	public void test_toString_toObject(ObjectMapper mapper) throws Exception {
		logger.debug("mapper="+mapper.getClass().getName());
		Video obj1 = Utils.createVideo() ;
		obj1.setStartDate(Utils.REF_DATE);

		String content = mapper.toString(obj1) ;
		//logger.debug("content="+content);
		Video obj2 = mapper.toObject(content,Video.class) ;
		assertEquals(obj1,obj2);
	}

	void assertEquals(Video obj1, Video obj2) {
		//logger.debug("obj1.time="+obj1.getStartDate()+" obj2.time="+obj2.getStartDate());
		Assert.assertEquals(obj1,obj2);
		Assert.assertEquals(obj1.getKeywords(),obj2.getKeywords());
		Assert.assertEquals(obj1.getAttributes(),obj2.getAttributes());
	}
}
