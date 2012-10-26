package com.amm.nosql.dao.mongodb;

import com.amm.nosql.dao.KeyStringValueDao;
import com.amm.nosql.data.KeyStringValue;
import com.mongodb.DBCollection;

public class MongodbKeyStringValueDao extends MongodbDao<KeyStringValue> implements KeyStringValueDao {
	public MongodbKeyStringValueDao(DBCollection dbcoll, MongodbObjectMapper<KeyStringValue> entityMapper) {
		super(dbcoll, entityMapper) ;
	}
}
