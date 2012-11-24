package com.amm.nosql.dao.citrusleaf;

import java.util.*;
import java.text.*;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import net.citrusleaf.CitrusleafInfo;

/**
 * Citrusleaf info stuff.
 * @author amesarovic
 */
public class CitrusleafDriver {
	private static final Logger logger = Logger.getLogger(CitrusleafDriver.class);

	public static void main(String [] args) throws Exception {
		(new CitrusleafDriver()).process(args);
	}

	void process(String [] args) throws Exception {
		if (args.length < 2) {
			error("Expecting: hostname port");
			return ;
		}
		String hostname = args[0];
		int port = Integer.parseInt(args[1]);
		String [] names = new String[0];
		if (args.length > 2) {
			names = new String[1];
			names[0] = args[2];
		}
		process(hostname, port, names) ;
	}

	void process(String hostname, int port, String [] names) throws Exception {
		Set<String> set = new HashSet<String>();
		set.add("features");
		set.add("statistics");

		CitrusleafInfo info = new CitrusleafInfo() ;
		Map<String,String> map = info.get(hostname,port,names);

		info("hostname="+hostname+" port="+port+" names="+Arrays.toString(names));
		if (map == null) {
			error("No info");
			return ;
		}

		for (Map.Entry<String,String> entry : map.entrySet() ) {
			String key = entry.getKey();
			if (key==null) continue;
			String value = entry.getValue();
			if (value==null) continue;
			//if (set.contains(key)) {
			if (value.contains(";")) {
				info("  "+key+":");
				String [] toks = value.split(";");
				for (String tok : toks) {
					String [] kv = tok.split("=");
					if (kv.length==2 && StringUtils.isNumeric(kv[1])) {
						long num = Long.parseLong(kv[1]);
						String str =  "";
						if (num > 9999) str = "  " +  withCommas(num);
						info("    "+tok+str);
					} else {
						info("    "+tok);
					}
				}
			} else {
				info("  "+key+": "+value);
			}
		}
	}

	public static final DecimalFormat formatter = new DecimalFormat("#,###");

	public static String withCommas(long num) {
		return String.format("%s",formatter.format(num));
	}

	void info(Object o) { System.out.println(o);}
	void error(Object o) { System.out.println("ERROR: "+o);}
}
