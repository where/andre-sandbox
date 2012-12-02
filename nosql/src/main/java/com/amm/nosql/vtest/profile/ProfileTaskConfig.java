package com.amm.nosql.vtest.profile;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.amm.nosql.vtest.NoSqlTaskConfig;
import com.amm.nosql.dao.ProfileDao;
import com.amm.vtest.BaseTask;
import com.amm.vtest.TaskConfig;

public class ProfileTaskConfig extends NoSqlTaskConfig {
	private ProfileDao keyValueDao ;

	public ProfileTaskConfig(ProfileDao keyValueDao) throws Exception {
		this.keyValueDao = keyValueDao ;
	}
	public ProfileDao getProfileDao() { return keyValueDao ; }
}
