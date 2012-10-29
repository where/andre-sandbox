package com.paypal.ppmn.rps.dao;

import java.util.*;
import org.apache.log4j.Logger;
import net.citrusleaf.CitrusleafClient;
import net.citrusleaf.CitrusleafClient.*;
import net.citrusleaf.CitrusleafClient.ClResult;
import net.citrusleaf.CitrusleafClient.ClResultCode;

/** TODO: Under construction */
public class CitrusleafKeyValueDao implements KeyValueDao {
	private static Logger logger = Logger.getLogger(CitrusleafKeyValueDao.class);
	private CitrusleafClient client;
	private String binName = "value" ;
	private String namespace ;
	private String set;

 	public CitrusleafKeyValueDao(CitrusleafClient client, String namespace, String set) {
		this.client = client;
		this.namespace = namespace;
		this.set = set;
	}

 	public Object get(String id) {
		Object obj = client.get(id);
		return obj;
	}

 	public Object _get(String id) {
		ClResult result = client.get(namespace,set,id,binName,null);
		//ClResult result = client.getAll(namespace,set,id,null);
/*
		if (result.resultCode != ClResultCode.OK) {
			throw new Exception(String.format(
				"Failed to get: namespace=%s set=%s key=%s bin=%s code=%s",
				params.namespace, params.set, key, bin, result.resultCode));
		}
*/
		Object value = result.results;
		return value;
	}

	public void put(String id, Object value) {
		ClResultCode resultCode = client.set(namespace, set, id, binName, value, null, null);

	//ClResultCode resultCode = client.set(namespace, set, id, value, null, null);
	}

	public void delete(String id) throws Exception {
		ClResultCode result = client.delete(namespace, set, id, null, null);
/*
		if (! (result == ClResultCode.OK || result == ClResultCode.KEY_NOT_FOUND_ERROR)) {
			throw new Exception(String.format("Failed to delete: ns=%s set=%s key=%s code=%s",
				namespace, set, id, result));
		}
*/
	}

}
