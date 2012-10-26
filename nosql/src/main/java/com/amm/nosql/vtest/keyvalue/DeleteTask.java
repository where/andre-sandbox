package com.amm.nosql.vtest.keyvalue;

import java.util.*;
import org.apache.log4j.Logger;
import java.util.concurrent.atomic.AtomicLong;
import com.amm.nosql.data.KeyValue;
import com.amm.vtest.TaskConfig;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;

public class DeleteTask extends KeyValueTask {
	private static final Logger logger = Logger.getLogger(DeleteTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private boolean doGet = false ;

	public DeleteTask(KeyValueTaskConfig config) throws Exception {
		super(config);
		setName("Delete");
	}

	public DeleteTask(KeyValueTaskConfig config, String name) throws Exception {
		super(config);
		setName(name);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		String key = getNextKey();
		logger.debug("count="+count+" key="+key+" doGet="+doGet);

		getKeyValueDao().delete(key);

		if (doGet) {
			KeyValue keyValue = getKeyValueDao().get(key);
			if (keyValue != null) 
				throw new FailureException("Should not find deleted key="+key);
		}
		count.getAndIncrement();
	}

	public void setDoGet(boolean doGet) {
		this.doGet = doGet;
	}
}
