package com.andre.pphere.rest.object;

import com.google.common.io.Files;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import com.andre.rest.data.Error;
import com.andre.pphere.data.Store;
import com.andre.pphere.data.StoreList;

import com.andre.pphere.rest.BaseTest;
import com.andre.mapper.ObjectMapper;
import com.andre.mapper.jackson.JacksonObjectMapper;
import com.andre.mapper.gson.GsonObjectMapper;

/**
 * Test JsonMapper implementations.
 */
public class JsonMapperTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(JsonMapperTest.class);
	private ObjectMapper jacksonMapper = new JacksonObjectMapper(true);
	private ObjectMapper gsonMapper = new GsonObjectMapper(true);

	@DataProvider(name = "jsonMappers")
	public Object[][] jsonMappers() {
		return new Object[][] {
			{ jacksonMapper }
			,{ gsonMapper }
		};
	}

	@Test(dataProvider = "jsonMappers")
	public void genre(ObjectMapper mapper) throws Exception {
		logger.debug("mapper="+mapper);
		Store obj1 = Utils.createStore("animals");
		String  content = mapper.toString(obj1);
		logger.debug("content="+content);
		writeFile("genre",content, mapper);

		Store obj2 = (Store)mapper.toObject(content,Store.class);

		Assert.assertEquals(obj1.getName(),obj2.getName());
		//Assert.assertEquals(obj1.getDescription(),obj2.getDescription());
		//Assert.assertEquals(obj1.getId(),obj2.getId());
	}

	@Test(dataProvider = "jsonMappers")
	public void genreList(ObjectMapper mapper) throws Exception {
		StoreList obj1 = Utils.createStoreList();
		String  content = mapper.toString(obj1);
		logger.debug("content="+content);
		writeFile("genres",content, mapper);
		//StoreList obj2 = (StoreList)mapper.toObject(content,StoreList.class);
	}

	@Test(dataProvider = "jsonMappers")
	public void error(ObjectMapper mapper) throws Exception {
		Error obj1 = new Error(400,"Ouch Happened", "AppErrorCode");

		String  content = mapper.toString(obj1);
		logger.debug("content="+content);
		writeFile("error",content, mapper);

		Error obj2 = (Error)mapper.toObject(content,Error.class);

		Assert.assertEquals(obj1.getHttpStatusCode(),obj2.getHttpStatusCode());
		Assert.assertEquals(obj1.getApplicationCode(),obj2.getApplicationCode());
		Assert.assertEquals(obj1.getMessage(),obj2.getMessage());
	}

	@Test(dataProvider = "jsonMappers")
	public void error2(ObjectMapper mapper) throws Exception {
		Error obj1 = new Error(400,"Ouch happened");

		String  content = mapper.toString(obj1);
		logger.debug("content="+content);
		writeFile("error2",content, mapper);

		Error obj2 = (Error)mapper.toObject(content,Error.class);

		Assert.assertEquals(obj1.getHttpStatusCode(),obj2.getHttpStatusCode());
		Assert.assertEquals(obj1.getApplicationCode(),obj2.getApplicationCode());
		Assert.assertEquals(obj1.getMessage(),obj2.getMessage());
	}

	void writeFile(String fname, String content, ObjectMapper mapper) throws IOException {
		String dirname = getName(mapper) ;
		File dir = new File("out/"+dirname);
		boolean sts = dir.mkdirs();
		File file = new File(dir, fname+".json");
		Files.write(content.getBytes(), file) ;
	}

	String getName(ObjectMapper mapper) {
		return mapper.getClass().getSimpleName();
	}
}
