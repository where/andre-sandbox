package com.amm.nosql.dao.hbase;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.amm.nosql.NoSqlException;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import com.amm.mapper.ObjectMapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.KeyValue;

/**
 * HBase implementation.
 * @author amesarovic
 */
public class HBaseNoSqlDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private static final Logger logger = Logger.getLogger(HBaseNoSqlDao.class);
	private Configuration config ;
	private HTable table ;
	private ObjectMapper<T> objectMapper;
	private byte[] family ;
	private String familyName ;
	private String tableName ;
	private String qualifierName ;
	private String host ;
	private byte [] qualifier ;

	public HBaseNoSqlDao(String host, String tableName, String familyName, String qualifierName, ObjectMapper<T> objectMapper) throws IOException {
        logger.debug("host="+host+" table="+tableName+" family="+familyName);
		config = HBaseConfiguration.create();
		this.host = host;
        config.set("hbase.zookeeper.quorum", host);
		this.table = new HTable(config, tableName);
		this.family = Bytes.toBytes(familyName);
		this.qualifier = Bytes.toBytes(qualifierName);
		this.tableName = tableName ;
		this.familyName = familyName;
		this.qualifierName = qualifierName;
		this.objectMapper = objectMapper ;
	}

	public T get(String key) throws Exception {
		Get get = new Get(key.getBytes());
		Result rs = table.get(get);
		byte [] value = rs.getValue(family, qualifier) ;
        logger.debug("get: key="+key+" family="+familyName+" qualifier="+qualifierName+" value="+value);
        if (value == null)
            return null ;
        T obj = objectMapper.toObject(value);
        obj.setKey(key);
		return obj;
	}

	public Map<String,T> getBulk(Collection<String> keys) throws Exception {
		return null;
	}

	public void put(T obj) throws Exception {
		String key = getKey(obj);
 		Put put = new Put(Bytes.toBytes(key));
		byte [] value = objectMapper.toBytes(obj);
        logger.debug("put: key="+key+" family="+familyName+" qualifier="+qualifierName+" value="+value);
		put.add(family, qualifier, value);
		table.put(put);
	}

	public void delete(String key) throws Exception {
		List<Delete> list = new ArrayList<Delete>();
		Delete del = new Delete(key.getBytes());
		list.add(del);
		table.delete(list);
	}

	private String getKey(T object) {
		return object.getKey().toString();
	}

    @Override
    public String toString() {
        return
            "host="+host
            +" table="+tableName
            +" family="+familyName
            +" qualifier="+qualifierName
            ;
	}
}
