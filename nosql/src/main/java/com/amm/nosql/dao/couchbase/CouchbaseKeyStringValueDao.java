package com.amm.nosql.dao.couchbase;

import java.io.IOException;
import java.net.URISyntaxException;

import com.amm.nosql.dao.KeyStringValueDao;
import com.amm.nosql.data.KeyStringValue;
import com.amm.mapper.ObjectMapper;

public class CouchbaseKeyStringValueDao extends CouchbaseDao<KeyStringValue> implements KeyStringValueDao {
	public CouchbaseKeyStringValueDao(String hostname, int port, int expiration, long opTimeout, long opQueueMaxBlockTime, String bucketname, ObjectMapper<KeyStringValue> entityMapper) throws IOException, URISyntaxException {
		super(hostname, port, expiration, opTimeout, opQueueMaxBlockTime, bucketname, entityMapper) ;
	}
}
