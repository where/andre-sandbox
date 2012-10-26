package com.amm.nosql.dao.memcached;

import java.io.IOException;
import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.amm.mapper.ObjectMapper;

public class MemcachedKeyValueDao extends MemcachedDao<KeyValue> implements KeyValueDao {
	public MemcachedKeyValueDao(String hostname, int port, int expiration, long timeout, ObjectMapper<KeyValue> entityMapper) throws IOException {
		super(hostname, port, expiration, timeout, entityMapper) ;
	}
	public MemcachedKeyValueDao(String hostname, int expiration, long timeout, ObjectMapper<KeyValue> entityMapper) throws IOException {
		super(hostname, expiration, timeout, entityMapper) ;
	}
}
