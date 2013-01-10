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
import com.andre.pphere.data.Store;
import com.andre.pphere.data.StoreList;
import com.andre.pphere.data.Location;

/**
 * Test ObjectMapper implementations.
 */
public class ObjectMapperTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(ObjectMapperTest.class);

	@Test(dataProvider = "objectMappers")
	public void store(ObjectMapper mapper) throws Exception {
		Store obj1 = Utils.createStore("foo");
		String str = mapper.toString(obj1);
		Store obj2 = (Store)mapper.toObject(str,Store.class);
		Assert.assertEquals(obj1.getName(),obj2.getName());
		assertEquals(obj1.getLocation(),obj2.getLocation());
	}

	@Test(dataProvider = "objectMappers")
	public void storeList(ObjectMapper mapper) throws Exception {
		StoreList obj1 = Utils.createStoreList();
		String str = mapper.toString(obj1);
		StoreList obj2 = (StoreList)mapper.toObject(str,StoreList.class);
	}

	@Test(dataProvider = "objectMappers")
	public void error(ObjectMapper mapper) throws Exception {
		Error obj1 = new Error(400,"ouch","oops");
		String str = mapper.toString(obj1);
		Error obj2 = (Error)mapper.toObject(str,Error.class);
		Assert.assertEquals(obj1.getHttpStatusCode(),obj2.getHttpStatusCode());
		Assert.assertEquals(obj1.getApplicationCode(),obj2.getApplicationCode());
		Assert.assertEquals(obj1.getMessage(),obj2.getMessage());
	}

	@Test(dataProvider = "objectMappers")
	public void location(ObjectMapper mapper) throws Exception {
		Store store = Utils.createStore("foo");
		Location loc1 = store.getLocation();
		String lstr1 = mapper.toString(loc1);
		Location loc2 = (Location)mapper.toObject(lstr1,Location.class);
		assertEquals(loc1,loc2);
	}

	@Test
	public void storeFile() throws Exception {
		ObjectMapper mapper = jsonMapper ;
		File file = new File("data/store.json");
		String str = new String(Files.toByteArray(file));
		Store obj = (Store)mapper.toObject(str,Store.class);
	}

	//@Test
	public void storeListFile() throws Exception {
		ObjectMapper mapper = jsonMapper ;
		File file = new File("data/stores.json");
		String str = new String(Files.toByteArray(file));
		//logger.debug("str="+str);
		StoreList obj1 = (StoreList)mapper.toObject(str,StoreList.class);
	}

	private void assertEquals(Location loc1, Location loc2) {
		Assert.assertEquals(loc1.getStreet1(),loc2.getStreet1());
		Assert.assertEquals(loc1.getStreet2(),loc2.getStreet2());
		Assert.assertEquals(loc1.getCity(),loc2.getCity());
	}

}
