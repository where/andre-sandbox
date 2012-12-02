package com.amm.nosql.dao.citrusleaf;

import java.util.*;
import java.text.*;
import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import net.citrusleaf.CitrusleafInfo;
import com.axm.web.dom.*;
import com.axm.web.dom.widget.TextProvider;
import com.axm.web.dom.widget.BaseReporterProvider;

/**
 * Citrusleaf info stuff.
 * @author amesarovic
 */
public class Citrusleaf2Driver {
	private static final Logger logger = Logger.getLogger(Citrusleaf2Driver.class);
	private BaseReporterProvider rprovider = new TextProvider();

	public static void main(String [] args) throws Exception {
		(new Citrusleaf2Driver()).process(args);
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

        DomTable table = new DomTable(1,2,0);
        DomRow row = table.addRow();
		row.addCell("Property").setAlign(DomCell.LEFT);
		row.addCell("Value").setAlign(DomCell.RIGHT);
		row.addCell("Nice").setAlign(DomCell.RIGHT);


		for (Map.Entry<String,String> entry : map.entrySet() ) {
			String key = entry.getKey();
			if (key==null) continue;
			String value = entry.getValue();
			if (value==null) continue;
			
			if (value.contains(";")) {
        		//row = table.addRow();
				//row.addCell(key).setAlign(DomCell.LEFT);
				//row.addCell("").setAlign(DomCell.RIGHT);
				//row.addCell("").setAlign(DomCell.RIGHT);
				info("-------------\n"+key+":");
				//info("  "+key+":");
				String [] toks = value.split(";");
				for (String tok : toks) {
					String [] kv = tok.split("=");
					if (kv.length==2 && StringUtils.isNumeric(kv[1])) {
        				row = table.addRow();
						long num = Long.parseLong(kv[1]);
						String str =  "";
						if (num > 9999) str = "  " +  withCommas(num);
						//info("    "+tok+str);
						row.addCell(kv[0]).setAlign(DomCell.LEFT);
						row.addCell(kv[1]).setAlign(DomCell.RIGHT);
						row.addCell(str).setAlign(DomCell.RIGHT);
					} else {
        				row = table.addRow();
						row.addCell(kv[0]).setAlign(DomCell.LEFT);
						value = kv.length==1 ? "" : kv[1];
						row.addCell(value).setAlign(DomCell.RIGHT);
						row.addCell("").setAlign(DomCell.RIGHT);
					}
				}
			} else {
				//info("  "+key+": "+value);
        		row = table.addRow();
				row.addCell(key).setAlign(DomCell.LEFT);
				row.addCell(value).setAlign(DomCell.RIGHT);
				row.addCell("").setAlign(DomCell.RIGHT);
			}
		}

		info("-------------");
        info(rprovider.convert(table));
	}

	public static final DecimalFormat formatter = new DecimalFormat("#,###");

	public static String withCommas(long num) {
		return String.format("%s",formatter.format(num));
	}

	void info(Object o) { System.out.println(o);}
	void error(Object o) { System.out.println("ERROR: "+o);}
}
