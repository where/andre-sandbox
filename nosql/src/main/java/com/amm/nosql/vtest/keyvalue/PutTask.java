package com.amm.nosql.vtest.keyvalue;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;
import com.amm.nosql.data.KeyValue;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;

public class PutTask extends KeyValueTask {
	private static final Logger logger = Logger.getLogger(PutTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private boolean debug = logger.isDebugEnabled();

	public PutTask(KeyValueTaskConfig config) throws Exception {
		super(config);
		setName("Put");
	}

	public PutTask(KeyValueTaskConfig config, String name) throws Exception {
		super(config);
		setName(name);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		try {
			String key = getNextKey();
			byte [] value = getValue().getBytes();

			KeyValue keyValue = new KeyValue(key,value);
			if (debug)
				logger.debug("count="+count + " keySize="+key.length() +" valueSize="+value.length
					+" key="+key
					+" value="+new String(value)
					);

			getKeyValueDao().put(keyValue);
		} finally {
			count.getAndIncrement();
		}
	}
}
