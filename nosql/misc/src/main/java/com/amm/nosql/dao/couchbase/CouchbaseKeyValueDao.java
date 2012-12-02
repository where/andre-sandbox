package com.amm.nosql.dao.couchbase;

import java.io.IOException;
import java.net.URISyntaxException;

import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.amm.mapper.ObjectMapper;

public class CouchbaseKeyValueDao extends CouchbaseDao<KeyValue> implements KeyValueDao {
	public CouchbaseKeyValueDao(String hostname, int port, int expiration, long timeout, String bucketname, ObjectMapper<KeyValue> entityMapper) throws IOException, URISyntaxException {
		super(hostname, port, expiration, timeout, bucketname, entityMapper) ;
	}
	public CouchbaseKeyValueDao(String hostname, int expiration, long timeout, String bucketname, ObjectMapper<KeyValue> entityMapper) throws IOException, URISyntaxException {
		super(hostname, expiration, timeout, bucketname, entityMapper) ;
	}
}
