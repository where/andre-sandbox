package com.amm.nosql.dao.hbase;

import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.amm.mapper.ObjectMapper;
import java.util.*;
import java.io.*;

public class HBaseKeyValueDao extends HBaseNoSqlDao<KeyValue> implements KeyValueDao {
    public HBaseKeyValueDao(String host, String tableName, String familyName, String qualifierName, ObjectMapper<KeyValue> objectMapper) throws IOException {
    	super(host, tableName, familyName, qualifierName, objectMapper) ;
	}
}
