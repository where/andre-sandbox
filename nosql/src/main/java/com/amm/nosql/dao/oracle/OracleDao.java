package com.amm.nosql.dao.oracle;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.amm.nosql.NoSqlException;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import com.amm.mapper.ObjectMapper;

import oracle.kv.KVStore;
import oracle.kv.Key;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;


/**
 * Implementation uses Oracle NoSQL.
 * @author amesarovic
 */
public class OracleDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private static final Logger logger = Logger.getLogger(OracleDao.class);
	private final ObjectMapper<T> objectMapper;
	private final KVStore store;

	public OracleDao(String url, String storeName, ObjectMapper<T> objectMapper) {
		this.objectMapper = objectMapper;
		logger.debug("url="+url+" store="+storeName);
		System.out.println(">> url="+url+" store="+storeName);
		store = KVStoreFactory.getStore(new KVStoreConfig(storeName, url));
	}

    @SuppressWarnings("unchecked")
	public T get(String key) throws Exception {
		ValueVersion valueVersion = store.get(Key.createKey(key));
		if (valueVersion == null)
			return null;
		byte [] value = valueVersion.getValue().getValue();
		T object = objectMapper.toObject(value);
		object.setKey(key);
		return object;
	}

	public void put(T object) throws Exception {
		String key = getKey(object);
		byte [] value = objectMapper.toBytes(object);
		store.put(Key.createKey(key), Value.createValue(value));
	}

	public void delete(String key) throws Exception {
		store.delete(Key.createKey(key));
	}

	private String getKey(T object) {
		return object.getKey().toString();
	}

	@Override 
	public String toString() {
		return
			"[class="+this.getClass().getName()
			+" objectMapper="+objectMapper
			+"]";
	}
}
