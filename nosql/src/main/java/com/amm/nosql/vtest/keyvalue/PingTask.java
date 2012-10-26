package com.amm.nosql.vtest.keyvalue;

import java.util.*;
import org.apache.log4j.Logger;
import java.util.concurrent.atomic.AtomicLong;
import com.amm.nosql.data.KeyValue;
import com.amm.vtest.TaskConfig;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;

public class PingTask extends KeyValueTask {
	private static final Logger logger = Logger.getLogger(PingTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private boolean checkValue ;
	private Random random = new Random(1);

	public PingTask(KeyValueTaskConfig config) throws Exception {
		super(config);
		setName("Ping");
	}

	public PingTask(KeyValueTaskConfig config, String name) throws Exception {
		super(config);
		setName(name);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		try {
			String key = ""+random.nextInt();
			logger.debug("count="+count+" key="+key);
			
			KeyValue keyValue = getKeyValueDao().get(key);
			if (checkValue && keyValue == null) 
				throw new FailureException("Cannot get key="+key+" count="+count);
		} finally {
			count.getAndIncrement();
		}
	}
}
