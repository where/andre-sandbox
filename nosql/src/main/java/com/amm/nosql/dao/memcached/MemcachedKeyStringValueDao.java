package com.amm.nosql.dao.memcached;

import java.io.IOException;
import com.amm.nosql.dao.KeyStringValueDao;
import com.amm.nosql.data.KeyStringValue;
import com.amm.mapper.ObjectMapper;

public class MemcachedKeyStringValueDao extends MemcachedDao<KeyStringValue> implements KeyStringValueDao {
	public MemcachedKeyStringValueDao(String hostname, int port, int expiration, long timeout, ObjectMapper<KeyStringValue> entityMapper) throws IOException {
		super(hostname, port, expiration, timeout, entityMapper) ;
	}
	public MemcachedKeyStringValueDao(String hostname, int expiration, long timeout, ObjectMapper<KeyStringValue> entityMapper) throws IOException {
		super(hostname, expiration, timeout, entityMapper) ;
	}
}
