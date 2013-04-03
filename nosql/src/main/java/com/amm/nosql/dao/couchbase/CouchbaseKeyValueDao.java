package com.amm.nosql.dao.couchbase;

import java.io.IOException;
import java.net.URISyntaxException;
import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.amm.mapper.ObjectMapper;
import net.spy.memcached.ops.OperationQueueFactory;

public class CouchbaseKeyValueDao extends CouchbaseDao<KeyValue> implements KeyValueDao {

	public CouchbaseKeyValueDao(String hostname, int port, int expiration, long opTimeout, long opQueueMaxBlockTime, String bucketname, ObjectMapper<KeyValue> entityMapper) throws IOException, URISyntaxException {
		super(hostname, port, expiration, opTimeout, opQueueMaxBlockTime, bucketname, entityMapper) ;
	}

	public CouchbaseKeyValueDao(String hostname, int port, int expiration, long opTimeout, long opQueueMaxBlockTime, String bucketname, ObjectMapper<KeyValue> entityMapper, OperationQueueFactory operationQueueFactory) throws IOException, URISyntaxException {
		super(hostname, port, expiration, opTimeout, opQueueMaxBlockTime, bucketname, entityMapper, operationQueueFactory) ;
	}
}
