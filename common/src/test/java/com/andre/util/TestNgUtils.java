package com.andre.util;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestNgUtils {
	private static final Logger logger = Logger.getLogger(TestNgUtils.class);

	public static Object[][] createObjectArray(Collection coll) {
		Assert.assertNotNull(coll,"Internal error - BaseTest.createObjectArray cannot accept null collection");
		Object[][] objects = new Object[coll.size()][1];
		int j=0;
		for (Object obj : coll)
			objects[j++][0] = obj ;
		return objects;
	}
}
