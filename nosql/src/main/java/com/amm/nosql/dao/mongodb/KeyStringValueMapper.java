package com.amm.nosql.dao.mongodb;

import java.util.*;
import com.amm.nosql.data.KeyStringValue;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;

/**
 * Maps KeyStringValue entity.
 * @author amesarovic
 */
public class KeyStringValueMapper implements MongodbObjectMapper<KeyStringValue> {
	private static final String COLUMN_ID = "_id";
	private String columnValueName = "value" ;

	public KeyStringValueMapper() {
	}

	public KeyStringValueMapper(String columnValueName) {
		this.columnValueName = columnValueName ;
	}

	public KeyStringValue toObject(DBObject dbobj) throws Exception {
		KeyStringValue kv = new KeyStringValue();
		kv.setKey((String)dbobj.get(COLUMN_ID));
		//kv.setValue((String)dbobj.get(columnValueName)); // TODO: mongo binary type
		String svalue = (String)dbobj.get(columnValueName);
		if (svalue != null)
			kv.setValue(svalue);
		return kv;
	}

	public DBObject fromObject(KeyStringValue kv) throws Exception {
		BasicDBObject dbobj = new BasicDBObject();
		dbobj.put(COLUMN_ID, kv.getKey());
		dbobj.put(columnValueName, new String(kv.getValue()));
		return dbobj ;
	}
}
