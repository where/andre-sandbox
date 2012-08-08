package com.andre.jaxrs;

import java.util.*;
import org.apache.log4j.Logger;
import javax.ws.rs.core.MultivaluedMap;

public class RestUtils {
    private static final Logger logger = Logger.getLogger(RestUtils.class);

	public static void dump(MultivaluedMap<String,String>	map) {
		info("Headers - size="+map.size());
		for (Map.Entry<String,List<String>> entry : map.entrySet()) {
			List<String> values = entry.getValue() ;
			String value = values.size() == 1 ? " value="+values.get(0) : "";
			info("  key="+entry.getKey()+value);
			if (values.size() > 1)
				for (String v : values)
					info("    "+v);
		}
	}

	static private void info (Object o) { System.out.println(""+o); }
}
