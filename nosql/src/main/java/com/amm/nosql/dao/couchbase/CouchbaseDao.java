package com.amm.nosql.dao.couchbase;

import java.util.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.internal.OperationFuture;

import org.apache.log4j.Logger;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import com.amm.mapper.ObjectMapper;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;

/**
 * Couchbase implementation.
 * @author rhariharan
 */
public class CouchbaseDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private static final Logger logger = Logger.getLogger(CouchbaseDao.class);
	private CouchbaseClient client ;
	private String hostnames ;
	private int port = 8091 ;
	private int expiration ;
	private long timeout ;
	private String bucketname;
	private String password = "" ;
	private ObjectMapper<T> entityMapper;
	private CouchbaseConnectionFactoryBuilder connectionFactoryBuilder = new CouchbaseConnectionFactoryBuilder();
	
	public CouchbaseDao(String hostname, int expiration, long timeout, String bucketname, ObjectMapper<T> entityMapper) throws IOException {
		this.hostnames = hostname ;
		this.expiration = expiration ;
		this.timeout = timeout ;
		this.bucketname = bucketname;
		this.entityMapper = entityMapper ;
		createClient();
	}

	public CouchbaseDao(String hostname, int port, int expiration, long timeout, String bucketname, ObjectMapper<T> entityMapper) throws IOException { 
		this.hostnames = hostname ;
		this.port = port ;
		this.expiration = expiration ;
		this.timeout = timeout ;
		this.bucketname = bucketname;
		this.entityMapper = entityMapper ;
		createClient();
	}

	private void createClient() throws IOException {
		connectionFactoryBuilder.setOpTimeout(timeout);
		String [] serverIPs = hostnames.split(",");
		List<URI> serverList = new ArrayList<URI>();  
        for (String serverName : serverIPs) {  
            URI base = URI.create(String.format("http://%s:%d/pools", serverName, port));  
            serverList.add(base);  
        }
		
		CouchbaseConnectionFactory cf = connectionFactoryBuilder.buildCouchbaseConnection(serverList, bucketname, password); 
		client = new CouchbaseClient(cf);
	}

	@SuppressWarnings("unchecked") 
	public T get(String id) throws Exception {
		byte [] value = (byte[])client.get(id);
		logger.debug("get: id="+id+" value="+value);
		if (value == null)
			return null ;
		T entity = entityMapper.toObject(value);
        entity.setKey(id);
		return entity;
	}

// If never call future.get, then value is never persisted!

	public void put(T entity) throws Exception {
		String key = getKey(entity);
		final byte [] value = entityMapper.toBytes(entity);
		logger.debug("put: id="+key+" value.length="+value.length);
		OperationFuture<Boolean> setOp = client.set(key, expiration, value);
		
		if (!setOp.get().booleanValue()) {
			logger.debug("Set failed: " + setOp.getStatus().getMessage());
			logger.debug("put: id="+key+" value.length="+value.length +" result= failure " + "timeout="+timeout);
			return;
	    }
		
		//Boolean result = timeout<0?null: future.get(timeout,TimeUnit.MILLISECONDS);
		logger.debug("put: id="+key+" value.length="+value.length +" result=success " + " timeout="+ timeout);
	}

	public void delete(String id) throws Exception {
		logger.debug("delete: id="+id);
		OperationFuture<Boolean> delOp = client.delete(id); 
		
		if (!delOp.get().booleanValue()) {
			logger.debug("delete failed: " + delOp.getStatus().getMessage());
			logger.debug("delete: id="+id+" result= failure");
	    } 
		
		logger.debug("delete: id="+id+" result= success");
	}

	private String getKey(T entity) {
		return entity.getKey().toString();
	}

    public void setExpiration(int seconds) {
		this.expiration = seconds ;
	}

    public void setTimeout(long timeout) {
		this.timeout = timeout ;
	}


	@Override 
	public String toString() {
		return 
			"hostname="+hostnames
			+" port="+port
			+" expiration="+expiration
			+" timeout="+timeout
		;
	}
}

