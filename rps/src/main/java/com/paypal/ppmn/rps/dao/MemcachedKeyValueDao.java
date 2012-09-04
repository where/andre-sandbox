package com.paypal.ppmn.rps.dao;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.andre.mapper.ObjectMapper;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.KetamaConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactory;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class MemcachedKeyValueDao implements KeyValueDao {
	private static final Map<String,Object> MAP = new HashMap<String,Object>();
	private static Logger logger = Logger.getLogger(MemcachedKeyValueDao.class);
	private MemcachedClient client ;
	private String hostname ;
	private int port ;
	private int expiration ;
	private long timeout ;
	//private ObjectMapper<Map<String,Object>> mapper;
	private ObjectMapper mapper;
	private ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();

 	public MemcachedKeyValueDao(MemcachedClient client, int expiration, long timeout, ObjectMapper mapper) throws IOException {
		this.expiration = expiration ;
		this.timeout = timeout ;
		this.mapper = mapper ;
		this.client = client ;
		logger.debug("client="+client+" expiration="+expiration+" timeout="+timeout);
	}

 	//public Map<String,Object> get(String id) throws Exception {
 	public Object get(String id) throws Exception {
		byte [] value = (byte[])client.get(id);
		logger.debug("get: id="+id+" value="+value);
		if (value == null)
			return null ;
		Map<String,Object> fields = mapper.toObject(value, MAP.getClass());
		return fields;
	}

	//public void put(String id, Map<String,Object> fields) throws Exception {
	public void put(String id, Object obj) throws Exception {
		final byte [] value = mapper.toBytes(obj);
		logger.debug("put: id="+id+" value.length="+value.length);
		Future<Boolean> future = client.set(id, expiration, value);
		Boolean result = timeout<0?null: future.get(timeout,TimeUnit.MILLISECONDS);
		logger.debug("put: id="+id+" value.length="+value.length +" result="+result+" timeout="+timeout);
	}

	public void delete(String id) throws Exception {
		logger.debug("delete: id="+id);
		Future<Boolean> future = client.delete(id);
		Boolean result = timeout<0?null: future.get(timeout,TimeUnit.MILLISECONDS);
		logger.debug("delete: id="+id+" result="+result);
	}

}
