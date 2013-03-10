package com.amm.nosql.dao.redis;

import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.amm.mapper.ObjectMapper;

public class RedisKeyValueDao extends RedisDao<KeyValue> implements KeyValueDao {
	public RedisKeyValueDao(String host, int port, ObjectMapper<KeyValue> objectMapper) {
		super(host,port,objectMapper);
	}
}
