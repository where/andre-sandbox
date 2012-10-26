package com.amm.nosql.vtest.keyvalue;

import java.util.*;
import org.apache.log4j.Logger;
import com.amm.nosql.data.KeyValue;
import com.amm.vtest.TaskConfig;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;

public class GetFailTask extends KeyValueTask {
	private static final Logger logger = Logger.getLogger(GetFailTask.class);

	public GetFailTask(KeyValueTaskConfig config) throws Exception {
		super(config);
		setName("GetFail");
	}

	public GetFailTask(KeyValueTaskConfig config, String name) throws Exception {
		super(config);
		setName(name);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		String key = getNextKey();
		logger.debug("key="+key);
		KeyValue keyValue = getKeyValueDao().get(key);
		if (keyValue != null) 
			throw new FailureException("GetFail: Should not find value for key="+key);
	}
}
