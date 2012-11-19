package com.amm.nosql.data;

import java.util.*;
import org.apache.commons.lang.StringUtils;

public class FormatUtils {

	public static void format(KeyValue keyValue) {
		format(keyValue,-1);
	}
	public static void format(KeyValue keyValue, int maxToDisplay) {
		if (keyValue == null) {
			info("KeyValue: null");
		} else {
			info("KeyValue:");
			info("  key="+keyValue.getKey());
			byte [] value = keyValue.getValue() ;
			String str = new String(value);
			if (maxToDisplay > -1 && str.length() > maxToDisplay)
				str = str.substring(0,maxToDisplay)+"...";
			//if (StringUtils.isAsciiPrintable(str))
			if (isAsciiPrintable(str)) {
				info("   #bytes="+value.length);
				info("  value="+str);
			} else {
				info("  value: #bytes="+value.length);
				//info("  value="+str);
			}
		}
	}

	public static void format(KeyStringValue keyValue) {
		if (keyValue == null) {
			info("KeyValue: null");
		} else {
			info("KeyValue:");
			info("  id="+keyValue.getKey());
			String value = keyValue.getValue() ;
			String str = new String(value);
			info("  value="+str);
		}
	}

	public static boolean isAsciiPrintable(String str) {
		for (int j=0 ; j < str.length() ; j++) {
			int ch = str.charAt(j);
			if (ch == 10) continue ;
			if (ch < 32 || ch > 127)  {
				//info(">> ch="+ch+" #idx="+j+" is not printable");
				return false ;
			}
		}
		return true ;
	}

	private static void info(Object o) { System.out.println(o);}
}
