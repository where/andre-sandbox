package com.amm.nosql.vtest.profile;

import java.util.*;
import org.apache.log4j.Logger;
import com.amm.vtest.BaseTask;
import com.amm.nosql.vtest.NoSqlTask;
import com.amm.nosql.dao.ProfileDao;

abstract public class ProfileTask extends NoSqlTask {
	private static final Logger logger = Logger.getLogger(ProfileTask.class);
	private ProfileDao keyValueDao ;

	public ProfileTask(ProfileTaskConfig config) throws Exception {
		super(config);
		keyValueDao = config.getProfileDao() ;
		logger.debug("keyValueDao=["+keyValueDao+"] keyGenerator="+getKeyGenerator()+" valueGenerator="+getValueGenerator());
	}

	public ProfileDao getProfileDao() { return keyValueDao ; }
}
