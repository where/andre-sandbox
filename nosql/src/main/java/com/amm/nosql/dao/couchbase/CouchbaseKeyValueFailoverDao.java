package com.amm.nosql.dao.couchbase;

import java.io.IOException;
import java.net.URISyntaxException;

import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.amm.mapper.ObjectMapper;

public class CouchbaseKeyValueFailoverDao extends CouchbaseFailoverDao<KeyValue> implements KeyValueDao {
	public CouchbaseKeyValueFailoverDao(String hostname, int port, int expiration, long opTimeout, long opQueueMaxBlockTime, String bucketname, ObjectMapper<KeyValue> entityMapper) throws IOException, URISyntaxException {
		super(hostname, port, expiration, opTimeout, opQueueMaxBlockTime, bucketname, entityMapper) ;
	}
}
