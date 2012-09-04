package com.paypal.ppmn.rps.mapper;

import com.paypal.ppmn.rps.BaseTest;
import java.util.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class MapperTest extends BaseTest {
	private static Logger logger = Logger.getLogger(MapperTest.class);
	private static final Map<String,Object> MAP = new HashMap<String,Object>();

	@Test
	public void testMe() throws Exception {

		Map<String,Object> fields = new HashMap<String,Object> ();
		fields.put("name","John Doe");
		fields.put("int", 2012);
		//fields.put("long", new Long(2013));
		fields.put("double", 12.12);

		logger.debug("fields="+fields.size());
		byte [] bytes = objectMapper.toBytes(fields);

		Map<String,Object> fields2 = (Map<String,Object>) objectMapper.toObject(bytes,MAP.getClass());
		logger.debug("fields2="+fields2.size());

		dump("fields",fields);
		dump("fields2",fields2);

		Assert.assertEquals(fields,fields2);

	}

	void dump(String msg, Map<String,Object> fields) {
		info(""+msg);
		for (Map.Entry<String,Object> entry : fields.entrySet() ) {
			Object value = entry.getValue();
			info("  entry: KEY="+entry.getKey()+" VALUE="+value+" TYPE="+value.getClass().getName());
		}
	}
}
