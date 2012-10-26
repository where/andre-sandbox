package com.amm.nosql.dao.noop;

import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;

public class NullKeyValueDao extends NullNoSqlDao<KeyValue> implements KeyValueDao {
	public NullKeyValueDao() throws Exception {
	}
}
