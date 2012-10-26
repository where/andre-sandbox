package com.amm.nosql.dao.mongodb;

import org.apache.log4j.Logger;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
//import com.mongodb.WriteResult;
//import com.mongodb.WriteConcern;

/**
 * MongoDB implementation.
 * @author amesarovic
 */
public class MongodbDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private static final Logger logger = Logger.getLogger(MongodbDao.class);
	private MongodbObjectMapper<T> entityMapper;
	private DBCollection dbcoll ;
	//private WriteConcern writeConcern = WriteConcern.FSYNC_SAFE;
	//private WriteConcern writeConcern = WriteConcern.NORMAL;

	public MongodbDao(DBCollection dbcoll, MongodbObjectMapper<T> entityMapper) {
		this.dbcoll = dbcoll;
		this.entityMapper = entityMapper;
		logger.debug("dbcoll="+dbcoll);
	}

	public T get(String id) throws Exception {
		logger.debug("get: id=" + id);
		BasicDBObject query = newIdQuery(id);

		DBCursor cursor = dbcoll.find(query);
		if (cursor.count() == 0)
			return null ;
		DBObject mobj = cursor.next();
		T entity = entityMapper.toObject(mobj);
		return entity;
	}

	public void put(T entity) throws Exception {
		logger.debug("put: entity="+entity);
		DBObject doc = entityMapper.fromObject(entity);

		//WriteResult result = dbcoll.save(doc,writeConcern);
		dbcoll.save(doc);  // updates too
	}

	public void delete(String id) throws Exception {
		BasicDBObject query = newIdQuery(id);
		dbcoll.remove(query);
	}

	private BasicDBObject newIdQuery(Object id) {
		BasicDBObject doc = new BasicDBObject();
		doc.put(Constants.COLUMN_ID, id);
		return doc;
	}

	@Override
	public String toString() {
		return
			"[class="+this.getClass().getName()
			+" DBcollection="+dbcoll.getName()
			+" Mongo={"+dbcoll.getDB().getMongo()+"}"
			//+" entityMapper="+entityMapper.getClass().getName()
			+" entityMapper={"+entityMapper+"}"
			+"]";
	}
}
