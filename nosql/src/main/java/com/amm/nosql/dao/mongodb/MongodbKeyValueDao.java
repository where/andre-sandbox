package com.amm.nosql.dao.mongodb;

import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.mongodb.DBCollection;

public class MongodbKeyValueDao extends MongodbDao<KeyValue> implements KeyValueDao {
	public MongodbKeyValueDao(DBCollection dbcoll, MongodbObjectMapper<KeyValue> entityMapper) {
		super(dbcoll, entityMapper) ;
	}
}
