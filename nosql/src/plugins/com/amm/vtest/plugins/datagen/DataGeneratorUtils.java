package com.amm.vtest.plugins.datagen;

import java.util.*;
import java.io.*;

/**
 * Data generation utilities.
 */
public class DataGeneratorUtils {
	public static String createString(int size) {
		return createString(size,"");
	}

	public static String createString(int size, String msg) {
		int len = msg.length();
		if (size < len)
			return msg.substring(0,len);

		StringBuilder sbuf = new StringBuilder(msg);

		for (int j=len ; j < size ; j++) {
			char c = j % 2 == 0 ? 'a' : '-' ;
			sbuf.append(c);
		}

		return sbuf.toString();
	}
}
