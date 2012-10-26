package com.amm.nosql.vtest.keyvalue;

import java.util.*;
import org.apache.log4j.Logger;
import com.amm.vtest.BaseTask;
import com.amm.nosql.vtest.NoSqlTask;
import com.amm.nosql.dao.KeyValueDao;

abstract public class KeyValueTask extends NoSqlTask {
	private static final Logger logger = Logger.getLogger(KeyValueTask.class);
	private KeyValueDao keyValueDao ;

	public KeyValueTask(KeyValueTaskConfig config) throws Exception {
		super(config);
		keyValueDao = config.getKeyValueDao() ;
		logger.debug("keyValueDao=["+keyValueDao+"] keyGenerator="+getKeyGenerator()+" valueGenerator="+getValueGenerator());
	}

	public KeyValueDao getKeyValueDao() { return keyValueDao ; }
}
