package com.andre.jaxb;

import java.util.*;

/**
 * Java Collection utilities.
 */
public class CollectionUtils {

	public static String toString(Collection coll) {
		return toString(coll, "	");
	}

	public static String toString(Collection coll, String sep) {
		if (coll==null) return null;
		StringBuilder sbuf = new StringBuilder(sep=="\n"?sep:"");
		for (Object o : coll)
			sbuf.append(""+o+sep);
		return sbuf.toString();
	}
}
