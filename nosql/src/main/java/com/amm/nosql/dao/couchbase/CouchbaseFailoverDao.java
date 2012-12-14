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
import java.util.concurrent.atomic.AtomicLong;

/**
 * Couchbase implementation with failover for put.
 * @author rhariharan, amesarovic
 */
public class CouchbaseFailoverDao<T extends NoSqlEntity> extends CouchbaseDao<T> {
	private static final Logger logger = Logger.getLogger(CouchbaseFailoverDao.class);
	private final static AtomicLong retryCount = new AtomicLong();
	private final static AtomicLong retryKeyCount = new AtomicLong();
	private final static AtomicLong retrySleptTime = new AtomicLong();
	private final static AtomicLong retrySuceededCount = new AtomicLong();

	public CouchbaseFailoverDao(String hostname, int expiration, long timeout, String bucketname, ObjectMapper<T> entityMapper) throws IOException { 
		super(hostname, expiration, timeout, bucketname, entityMapper) ;
	}
	
	public CouchbaseFailoverDao(String hostname, int port, int expiration, long timeout, String bucketname, ObjectMapper<T> entityMapper) throws IOException { 
		super(hostname, expiration, timeout, bucketname, entityMapper) ;
	}

	public void put(T entity) throws Exception {
		String emsg = "";
		long _retrySleepTime = 0;
		for (int j=0 ; j < retryMax ; j++) {
			_retrySleepTime += retrySleepTime;
			emsg = _put(entity);
			if (emsg == null) {
				if (j > 0) {
					retrySuceededCount.getAndIncrement();
					System.out.println("RETRY SUCCEEDED: key="+getKey(entity)
						+" j="+j+"/"+retryMax
						+" retryCount="+retryCount
						+" retryKeyCount="+retryKeyCount
						+" retrySuceededCount="+retrySuceededCount
						+" retrySleepTime="+retrySleepTime+"/"+_retrySleepTime+" retrySleptTime=" +retrySleptTime);
				}
				return ;
			}
			retryCount.getAndIncrement();
			if (j==0)
				retryKeyCount.getAndIncrement();
			Thread.sleep(_retrySleepTime);
			retrySleptTime.getAndAdd(_retrySleepTime);
		}
		emsg += ". Exceeded retryMax="+retryMax
			+" (retryCount="+retryCount
			+" retryKeyCount="+retryKeyCount
			+" retrySuceededCount="+retrySuceededCount
			+" retrySleepTime="+retrySleepTime+"/"+_retrySleepTime+" retrySleptTime=" +retrySleptTime
			+")";
		throw new NoSqlException(emsg);
	}

	private String _put(T entity) throws Exception {
		String key = getKey(entity);
		final byte [] value = entityMapper.toBytes(entity);
		logger.debug("put: key="+key+" value.length="+value.length);
		OperationFuture<Boolean> op = client.set(key, expiration, value);
		
		if (!op.get().booleanValue()) {
			String emsg = "Failed to set: key=" +key+" value.length="+value.length + " Op.status="+op.getStatus().getMessage();
			logger.debug("RETRY: "+emsg);
			return emsg ;
		}
		
		return null;
	}

	private int retryMax; 
	public int getRetryMax() { return retryMax; }
	public void setRetryMax(int retryMax) { this.retryMax = retryMax; } 

	private long retrySleepTime; 
	public long getRetrySleepTime() { return retrySleepTime; }
	public void setRetrySleepTime(long retrySleepTime) { this.retrySleepTime = retrySleepTime; } 

	@Override 
	public String toString() {
		return 
			super.toString()
			+" retryMax="+retryMax
			+" retrySleepTime="+retrySleepTime
			+" retrySleptTime="+retrySleptTime
			+" retryKeyCount="+retryKeyCount
			+" retrySuceededCount="+retrySuceededCount
		;
	}
}
