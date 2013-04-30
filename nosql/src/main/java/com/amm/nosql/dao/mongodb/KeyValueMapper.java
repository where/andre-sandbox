package com.amm.nosql.dao.mongodb;

import java.util.*;
import com.amm.nosql.data.KeyValue;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;

/**
 * Maps KeyValue entity.
 * @author amesarovic
 */
public class KeyValueMapper implements MongodbObjectMapper<KeyValue> {
	private static final String COLUMN_ID = "_id";
	private String columnValueName = "value" ;

	public KeyValueMapper() {
	}

	public KeyValueMapper(String columnValueName) {
		this.columnValueName = columnValueName ;
	}

	public KeyValue toObject(DBObject dbobj) throws Exception {
		KeyValue kv = new KeyValue();
		kv.setKey((String)dbobj.get(COLUMN_ID));
		//kv.setValue((String)dbobj.get(columnValueName)); // TODO: mongo binary type
		byte [] bytes = (byte[])dbobj.get(columnValueName);
		if (bytes != null)
			kv.setValue(bytes);
		return kv;
	}

	public DBObject fromObject(KeyValue entity) throws Exception {
		BasicDBObject dbobj = new BasicDBObject();
		dbobj.put(COLUMN_ID, entity.getKey());
		dbobj.put(columnValueName, entity.getValue());
		return dbobj ;
	}
}
