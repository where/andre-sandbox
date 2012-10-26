package com.amm.nosql.dao.mongodb;

import com.amm.nosql.data.NoSqlEntity;
import com.mongodb.DBObject;

/**
 * Maps entity to/from a Mongo DBObject.
 * @author amesar
 */
public interface MongodbObjectMapper<T extends NoSqlEntity> {
	public T toObject(DBObject dbobj) throws Exception;
	public DBObject fromObject(T entity) throws Exception;
}
