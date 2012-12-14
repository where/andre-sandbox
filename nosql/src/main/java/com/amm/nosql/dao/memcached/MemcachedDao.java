package com.amm.nosql.dao.memcached;

import java.util.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import com.amm.mapper.ObjectMapper;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.KetamaConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.AddrUtil;

/**
 * Memcached implementation - can be used either for memcached or membase.
 * @author amesarovic
 */
public class MemcachedDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private static final Logger logger = Logger.getLogger(MemcachedDao.class);
	private MemcachedClient client ;
	private String hostname ;
	private int port ;
	private int expiration ;
	private long timeout ;
	private ObjectMapper<T> entityMapper;
	private ConnectionFactoryBuilder connectionFactoryBuilder = new ConnectionFactoryBuilder();

	public MemcachedDao(MemcachedClient client, int expiration, long timeout, ObjectMapper<T> entityMapper) throws IOException {
		this.expiration = expiration ;
		this.timeout = timeout ;
		this.entityMapper = entityMapper ;
		this.client = client ;
		logger.debug("client="+client+" expiration="+expiration+" timeout="+timeout);
	}

	public MemcachedDao(String hostnames, int expiration, long timeout, ObjectMapper<T> entityMapper) throws IOException {
		this.hostname = hostnames ;
		this.expiration = expiration ;
		this.timeout = timeout ;
		this.entityMapper = entityMapper ;

		connectionFactoryBuilder.setOpTimeout(timeout);
		ConnectionFactory connectionFactory = connectionFactoryBuilder.build();

   		List<InetSocketAddress> addresses = AddrUtil.getAddresses(hostnames);

		client = new MemcachedClient(connectionFactory, addresses);
	}

	public MemcachedDao(String hostname, int port, int expiration, long timeout, ObjectMapper<T> entityMapper) throws IOException {
		this.hostname = hostname ;
		this.port = port ;
		this.expiration = expiration ;
		this.timeout = timeout ;
		this.entityMapper = entityMapper ;

		client = new MemcachedClient(new InetSocketAddress(hostname, port));
		logger.debug("client="+client+" expiration="+expiration+" timeout="+timeout);

		connectionFactoryBuilder.setOpTimeout(timeout);
		ConnectionFactory connectionFactory = connectionFactoryBuilder.build();
		List<InetSocketAddress> addresses = Arrays.asList(new InetSocketAddress(hostname, port));
		client = new MemcachedClient(connectionFactory, addresses);

/*
		KetamaConnectionFactory kf = new KetamaConnectionFactory();
		List<InetSocketAddress> addresses = Arrays.asList(new InetSocketAddress(hostname, port));
		client = new MemcachedClient(kf,addresses);
		//client = new MemcachedClient(kf,new InetSocketAddress(hostname, port));
		//kf.setOperationTimeout(11);
		logger.debug("OperationTimeout="+kf.getOperationTimeout());
*/
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

	public Map<String,T> getBulk(Collection<String> keys) throws Exception {
		throw new UnsupportedOperationException(); // TODO
	}

// If never call future.get, then value is never persisted!
	public void put(T entity) throws Exception {
		String key = getKey(entity);
		final byte [] value = entityMapper.toBytes(entity);
		logger.debug("put: id="+key+" value.length="+value.length);
		Future<Boolean> future = client.set(key, expiration, value);
		Boolean result = timeout<0?null: future.get(timeout,TimeUnit.MILLISECONDS);
		logger.debug("put: id="+key+" value.length="+value.length +" result="+result+" timeout="+timeout);
	}

	public void delete(String id) throws Exception {
		logger.debug("delete: id="+id);
		Future<Boolean> future = client.delete(id); 
		Boolean result = timeout<0?null: future.get(timeout,TimeUnit.MILLISECONDS);
		logger.debug("delete: id="+id+" result="+result);
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
			"hostname="+hostname
			+" port="+port
			+" expiration="+expiration
			+" timeout="+timeout
		;
	}
}
