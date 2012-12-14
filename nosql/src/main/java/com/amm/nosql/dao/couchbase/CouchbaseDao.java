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
import com.amm.nosql.NoSqlException;
import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import net.spy.memcached.ops.OperationQueueFactory;

/**
 * Couchbase implementation.
 * @author rhariharan
 */
public class CouchbaseDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private static final Logger logger = Logger.getLogger(CouchbaseDao.class);
	protected CouchbaseClient client ;
	private String hostnames ;
	private int port = 8091 ;
	protected int expiration ;
	protected long opTimeout ;
	protected long opQueueMaxBlockTime;
	private String bucketname;
	private String password = "" ;
	private OperationQueueFactory operationQueueFactory ;
	protected ObjectMapper<T> entityMapper;
	private CouchbaseConnectionFactoryBuilder connectionFactoryBuilder = new CouchbaseConnectionFactoryBuilder();
	
/*
	public CouchbaseDao(String hostname, int expiration, long opTimeout, String bucketname, ObjectMapper<T> entityMapper) throws IOException {
		this.hostnames = hostname ;
		this.expiration = expiration ;
		this.opTimeout = opTimeout ;
		this.bucketname = bucketname;
		this.entityMapper = entityMapper ;
		createClient();
	}
*/

	public CouchbaseDao(String hostname, int port, int expiration, long opTimeout, long opQueueMaxBlockTime, String bucketname, ObjectMapper<T> entityMapper) throws IOException { 
		this.hostnames = hostname ;
		this.port = port ;
		this.expiration = expiration ;
		this.opTimeout = opTimeout ;
		this.opQueueMaxBlockTime = opQueueMaxBlockTime ;
		this.bucketname = bucketname;
		this.entityMapper = entityMapper ;
		createClient();
	}

	public CouchbaseDao(String hostname, int port, int expiration, long opTimeout, long opQueueMaxBlockTime, String bucketname, ObjectMapper<T> entityMapper, OperationQueueFactory operationQueueFactory) throws IOException { 
		this.hostnames = hostname ;
		this.port = port ;
		this.expiration = expiration ;
		this.opTimeout = opTimeout ;
		this.opQueueMaxBlockTime = opQueueMaxBlockTime ;
		this.bucketname = bucketname;
		this.entityMapper = entityMapper ;
		this.operationQueueFactory = operationQueueFactory ;
		createClient();
	}

	private void createClient() throws IOException {
		connectionFactoryBuilder.setOpTimeout(opTimeout);
		connectionFactoryBuilder.setOpQueueMaxBlockTime(opQueueMaxBlockTime);   
		logger.debug("opTimeout="+opTimeout+" opQueueMaxBlockTime="+opQueueMaxBlockTime);
		logger.debug("operationQueueFactory="+operationQueueFactory);
		if (operationQueueFactory != null)
			connectionFactoryBuilder.setOpQueueFactory(operationQueueFactory);

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
	public T get(String key) throws Exception {
		byte [] value = (byte[])client.get(key);
		logger.debug("get: key="+key+" value="+value);
		if (value == null)
			return null ;
		T entity = entityMapper.toObject(value);
        entity.setKey(key);
		return entity;
	}

	public Map<String,T> getBulk(Collection<String> keys) throws Exception {
		Map<String,Object> omap = client.getBulk(keys);
		Map<String,T> tmap = new HashMap<String,T>();
		for (Map.Entry<String,Object> entry : omap.entrySet() ) {
			String key = entry.getKey();
			byte [] value = (byte[])entry.getValue();
			T entity = entityMapper.toObject(value);
        	entity.setKey(key);
			tmap.put(key,entity);
		}
		return tmap;
	}

// If never call future.get, then value is never persisted!

	public void put(T entity) throws Exception {
		String key = getKey(entity);
		final byte [] value = entityMapper.toBytes(entity);
		logger.debug("put: key="+key+" value.length="+value.length);
		OperationFuture<Boolean> op = client.set(key, expiration, value);
		
		if (!op.get().booleanValue()) {
			String emsg = "put failed: key=" +key+" value.length="+value.length+" Op.status="+op.getStatus().getMessage();
			logger.debug(emsg);
			throw new NoSqlException(emsg);
	    }
		
		//Boolean result = timeout<0?null: future.get(timeout,TimeUnit.MILLISECONDS);
		logger.debug("put: key="+key+" value.length="+value.length +" result=success");
	}

	public void delete(String key) throws Exception {
		logger.debug("delete: key="+key);
		OperationFuture<Boolean> op = client.delete(key); 
		
		if (!op.get().booleanValue()) {
			String emsg = "Failed to delete: key=" +key+ " Op.status="+op.getStatus().getMessage();
			logger.debug(emsg);
			return;
	    } 
		logger.debug("delete: key="+key+" result=success");
	}

	protected String getKey(T entity) {
		return entity.getKey().toString();
	}

    public void setExpiration(int seconds) {
		this.expiration = seconds ;
	}

	@Override 
	public String toString() {
		return 
			"hostname="+hostnames
			+" port="+port
			+" expiration="+expiration
			+" opTimeout="+opTimeout
			+" opQueueMaxBlockTime="+opQueueMaxBlockTime
			+" operationQueueFactory="+operationQueueFactory
		;
	}
}

