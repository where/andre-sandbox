package com.amm.nosql.dao.oracle;

import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.amm.mapper.ObjectMapper;

public class OracleKeyValueDao extends OracleDao<KeyValue> implements KeyValueDao {
	public OracleKeyValueDao(String url, String storeName, ObjectMapper<KeyValue> entityMapper) throws Exception {
		super(url, storeName, entityMapper) ;
	}
}
