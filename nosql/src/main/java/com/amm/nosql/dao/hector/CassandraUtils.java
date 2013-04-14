package com.amm.nosql.dao.cassandra;

import me.prettyprint.hector.api.beans.HColumn;

/**
 * Cassandra utilities.
 * @author amesarovic
 */
public class CassandraUtils {

	public static String format(HColumn column) {
		if (column==null) return null;
		return 
			"[Name="+column.getName()
			+ " Clock="+ column.getClock()
			+ " Ttl="+ column.getTtl()
			+ "]"
			;
	}
	
}
