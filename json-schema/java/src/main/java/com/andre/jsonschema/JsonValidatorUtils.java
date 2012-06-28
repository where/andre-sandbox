package com.andre.jsonschema;

import java.util.*;
import java.io.*;

public class JsonValidatorUtils {
	
	public static void report(File instanceFile, List<String> array) {
		if (0 == array.size()) {
			info(instanceFile+" validates");
		} else {
			info(array.size()+ " Errors: ");
			for (String str : array)
				info("  "+str);
		}
	}

	private static void info(Object o) { System.out.println(o);}
}
