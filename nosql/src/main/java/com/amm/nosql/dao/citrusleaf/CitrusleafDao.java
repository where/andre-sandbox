package com.amm.nosql.dao.citrusleaf;

import java.util.*;
import org.apache.log4j.Logger;

import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import com.amm.mapper.ObjectMapper;
import com.amm.nosql.NoSqlException;

import net.citrusleaf.CitrusleafClient;
import net.citrusleaf.CitrusleafClient.ClResult;
import net.citrusleaf.CitrusleafClient.ClResultCode;

/**
 * CitrusleafDao implementation.
 * @author amesarovic
 */
public class CitrusleafDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private static final Logger logger = Logger.getLogger(CitrusleafDao.class);
	private ObjectMapper<T> mapper;
	private CitrusleafClient client ;
	private String bin ;
	private String set ;
	private String namespace ;

	public CitrusleafDao(String hostnames, String namespace, String set, String bin, ObjectMapper<T> mapper) throws Exception {
		this.client = new CitrusleafClient();
		this.set = set ;
		this.bin = bin ;
		this.namespace = namespace ;
		this.mapper = mapper ;
		logger.debug("client="+client+" namespace="+namespace+" set="+set+" bin="+bin);
		String [] list = hostnames.split(",");
		for (String hostname : list) {
			logger.debug("Adding hostname: "+hostname);
			//System.out.println(">> ADDING HOST: "+hostname);
			client.addHost(hostname,3000);
		}
	}

	public CitrusleafDao(CitrusleafClient client, String namespace, String set, String bin, ObjectMapper<T> mapper) throws Exception {
		this.client = client ;
		this.set = set ;
		this.bin = bin ;
		this.namespace = namespace ;
		this.mapper = mapper ;
		logger.debug("client="+client+" namespace="+namespace+" set="+set+" bin="+bin);
	}

	@SuppressWarnings("unchecked") 
	public T get(String key) throws Exception {
		logger.debug("get: key="+key);
        ClResult result = client.get(namespace, set, key, bin, null);
		logger.debug("get: key="+key+" result="+result);

        if (result.resultCode == ClResultCode.KEY_NOT_FOUND_ERROR) {
			return null;
		} else if (result.resultCode != ClResultCode.OK) {
            throw new NoSqlException(String.format(
                "Failed to get: namespace=%s set=%s key=%s bin=%s code=%s",
                namespace, set, key, bin, result.resultCode));
        }
		byte [] value = (byte[])result.result;
		if (value == null)
			return null ;
		T entity = mapper.toObject(value);
        entity.setKey(key);
		return entity;
	}

	public Map<String,T> getBulk(Collection<String> keys) throws Exception { // TODO
/* 
        ClResult result = client.get(namespace, set, keys, bin, null);
*/
		throw new UnsupportedOperationException();
	}

	public void put(T entity) throws Exception {
		String key = getKey(entity);
		final byte [] value = mapper.toBytes(entity);
		logger.debug("put: id="+key+" value.length="+value.length);

        ClResultCode r = client.set(namespace, set, key, bin, value, null, null);

        if (r != ClResultCode.OK) {
            throw new NoSqlException(String.format("Failed to set: key=%s code=%s", key, r));
        }
	}

	public void delete(String id) throws Exception {
		ClResultCode r = client.delete(namespace, set, id, null, null);
        //if (! (r == ClResultCode.OK )) {
        if (! (r == ClResultCode.OK || r == ClResultCode.KEY_NOT_FOUND_ERROR)) {
            throw new NoSqlException(String.format("Failed to delete: ns=%s set=%s key=%s code=%s",
                namespace, set, id, r));
		}
		logger.debug("delete: id="+id);
	}

    private String getKey(T entity) {
        return entity.getKey().toString();
    }

	public void setHost(String host) {
		logger.debug("host="+host);
		client.addHost(host,3000);
	}

	//public void setHosts(List<String> hosts) {
	//}

	@Override 
	public String toString() {
		return 
			" namespace="+namespace
			+" set="+set
			+" bin="+bin
			+" client="+client
		;
	}
}
