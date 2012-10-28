package com.amm.nosql.dao.citrusleaf;

import java.util.*;
import org.apache.log4j.Logger;
import net.citrusleaf.CitrusleafInfo;

/**
 * Citrusleaf info stuff.
 * @author amesarovic
 */
public class CitrusleafDriver {
	private static final Logger logger = Logger.getLogger(CitrusleafDriver.class);
	private String hostname ;
	private int port ;

	public static void main(String [] args) throws Exception {
		(new CitrusleafDriver()).process(args);
	}

	void process(String [] args) throws Exception {
		if (args.length < 2) {
			error("Expecting: hostname port");
			return ;
		}
		hostname = args[0];
		port = Integer.parseInt(args[1]);
		Set<String> set = new HashSet<String>();
		set.add("features");
		set.add("statistics");

		CitrusleafInfo info = new CitrusleafInfo() ;
		Map<String,String> map = info.get(hostname,port);
		info("hostname="+hostname+" port="+port);
		for (Map.Entry<String,String> entry : map.entrySet() ) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (set.contains(key)) {
				info("  "+key+":");
				String [] toks = value.split(";");
				for (String tok : toks)
					info("    "+tok);
			} else {
				info("  "+key+": "+value);
			}
		}
	}

	void info(Object o) { System.out.println(o);}
	void error(Object o) { System.out.println("ERROR: "+o);}
}
