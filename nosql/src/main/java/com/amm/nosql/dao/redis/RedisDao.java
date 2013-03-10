package com.amm.nosql.dao.redis;

import java.util.*;
import org.apache.log4j.Logger;
import com.amm.nosql.NoSqlException;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import com.amm.mapper.ObjectMapper;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis - Jedis - implementation.
 * @author amesarovic
 */
public class RedisDao<T extends NoSqlEntity> implements NoSqlDao<T> {
    private static final Logger logger = Logger.getLogger(RedisDao.class);
    private String host ;
    private int port ;
    private JedisPool jedisPool ;
	private ObjectMapper<T> objectMapper;

    public RedisDao(String host, int port, ObjectMapper<T> objectMapper) {
        jedisPool = new JedisPool(host, port);
        this.objectMapper = objectMapper;
        this.host = host;
        this.port = port;
        logger.debug("host="+host+" port="+port);
    }

    //@SuppressWarnings("unchecked") // TODO
    public T get(String key) throws Exception {
        Jedis jedis = getJedis();
        String svalue = jedis.get(key);
        returnJedis(jedis) ;
        if (svalue == null)
            return null;
        byte [] value = svalue.getBytes();
        T object = objectMapper.toObject(value);
        object.setKey(key);
        return object;
    }

	public Map<String,T> getBulk(Collection<String> keys) throws Exception {
		return null;
	}

    public void put(T object) throws Exception {
        String key = getKey(object);
        logger.debug("put: key="+key);
        byte [] value = objectMapper.toBytes(object);
        String svalue = new String(value);
        Jedis jedis = getJedis();
        jedis.set(key, svalue);
        returnJedis(jedis) ;
    }

    public void delete(String key) throws Exception {
        Jedis jedis = getJedis();
        Long result = jedis.del(key);
        returnJedis(jedis) ;
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }
    private void returnJedis(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    public void finalize() {
        jedisPool.destroy();
    }

    private String getKey(T object) {
        return object.getKey().toString();
    }

    @Override
    public String toString() {
        return
            "[class="+this.getClass().getName()
            +" host="+host
            +" port="+port
            +" jedisPool="+jedisPool
            +"]";
    }

}
