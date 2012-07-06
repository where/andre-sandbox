package com.andre.mapper;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.mapper.jackson.JacksonObjectMapper;
import com.andre.mapper.gson.GsonObjectMapper;
import com.andre.mapper.javaser.JavaSerObjectMapper;
import com.andre.mapper.jaxb.JaxbObjectMapper;
import com.andre.util.TestNgUtils;

public class ObjectMapperTest {
	private static final Logger logger = Logger.getLogger(ObjectMapperTest.class);
	private List<ObjectMapper> mappers = new ArrayList<ObjectMapper>();

	@DataProvider(name = "mappers")
	public Object[][] mappers() {
		return TestNgUtils.createObjectArray(mappers) ;
	}

	@BeforeClass
	void beforeClass() throws Exception {
		mappers.add(new JacksonObjectMapper());
		mappers.add(new GsonObjectMapper());
		mappers.add(new JavaSerObjectMapper());
		mappers.add(new JaxbObjectMapper());
	}

	@Test(dataProvider = "mappers")
	public void testToBytes(ObjectMapper mapper) throws Exception {
		logger.debug("mapper="+mapper.getClass().getName());
		Video obj1 = Utils.createVideo() ;

		byte [] content = mapper.toBytes(obj1) ;
		Video obj2 = mapper.toObject(content,Video.class) ;
		assertEquals(obj1,obj2);

	}

	@Test(dataProvider = "mappers")
	public void testToString(ObjectMapper mapper) throws Exception {
		logger.debug("mapper="+mapper.getClass().getName());
		Video obj1 = Utils.createVideo() ;

		String content = mapper.toString(obj1) ;
		Video obj2 = mapper.toObject(content,Video.class) ;
		assertEquals(obj1,obj2);
	}

	void assertEquals(Video obj1, Video obj2) {
		Assert.assertEquals(obj1,obj2);
		Assert.assertEquals(obj1.getKeywords(),obj2.getKeywords());
		Assert.assertEquals(obj1.getAttributes(),obj2.getAttributes());
	}
}
