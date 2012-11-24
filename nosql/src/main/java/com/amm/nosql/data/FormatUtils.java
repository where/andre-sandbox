package com.amm.nosql.data;

import java.util.*;
import org.apache.commons.lang.StringUtils;

public class FormatUtils {

	public static void print(KeyValue keyValue) {
		print(keyValue,-1);
	}
	public static void print(KeyValue keyValue, int maxToDisplay) {
		if (keyValue == null) {
			info("KeyValue: null");
		} else {
			info("KeyValue:");
			info("  key="+keyValue.getKey());
			byte [] value = keyValue.getValue() ;
			String str = new String(value);
			if (maxToDisplay > -1 && str.length() > maxToDisplay)
				str = str.substring(0,maxToDisplay)+"...";
			print(str,value);
		}
	}

	public static void print(String str) {
		print(str,str.getBytes());
	}
	public static void print(String str, byte [] value) {
		if (isAsciiPrintable(str)) {
			info("  #valueBytes="+value.length);
			info("  value="+str);
		} else {
			info("  value: #bytes="+value.length);
			//info("  value="+str);
		}
	}

	public static void print(KeyStringValue keyValue) {
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

	public static void dump(byte [] content) {
		info("Dump bytes - length="+content.length);
		for (int j=0 ; j < content.length ; j++) {
			info("  "+j+". "+content[j]);
		}
	}

	private static void info(Object o) { System.out.println(o);}
}
