package com.amm.nosql.dao.memcached;

import java.io.IOException;
import com.amm.nosql.dao.ProfileDao;
import com.amm.nosql.data.Profile;
import com.amm.mapper.ObjectMapper;

public class MemcachedProfileDao extends MemcachedDao<Profile> implements ProfileDao {
	public MemcachedProfileDao(String hostname, int port, int expiration, long timeout, ObjectMapper<Profile> entityMapper) throws IOException {
		super(hostname, port, expiration, timeout, entityMapper) ;
	}
	public MemcachedProfileDao(String hostname, int expiration, long timeout, ObjectMapper<Profile> entityMapper) throws IOException {
		super(hostname, expiration, timeout, entityMapper) ;
	}
}
