package com.amm.nosql.vtest.keyvalue;

import com.amm.nosql.data.KeyValue;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;

public class PutOrGetTask extends KeyValueTask {
	private static final Logger logger = Logger.getLogger(PutOrGetTask.class);
	private volatile AtomicLong count = new AtomicLong(0);
	private boolean debug = logger.isDebugEnabled();
	private int mod = 2 ;

	public PutOrGetTask(KeyValueTaskConfig config) throws Exception {
		super(config);
		setName("PutOrGet");
		this.mod = mod ;
	}

	public PutOrGetTask(KeyValueTaskConfig config, String name) throws Exception {
		super(config);
		setName(name);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		try {
			String key = getNextKey();
			if (count.get() % mod == 0) {
				put(key);
			} else {
				get(key);
			}
		} finally  {
			count.getAndIncrement();
		}
	}

	private KeyValue put(String key) throws FailureException, Exception {
		//System.out.println(">> put: key="+key);
		byte [] value = getValue().getBytes();

		KeyValue keyValue = new KeyValue(key,value);
		if (debug)
			logger.debug("count="+count + " keySize="+key.length() +" valueSize="+value.length
				+" key="+key +" value="+new String(value)
				);

		getKeyValueDao().put(keyValue);
		return keyValue;
	}

	private void get(String key) throws FailureException, Exception {
		//System.out.println(">> get: key="+key);
		KeyValue keyValue2 = getKeyValueDao().get(key);
		if (keyValue2 == null)
			throw new FailureException("Cannot get key="+key);
	}

	public void setModulo(int mod) {
		this.mod = mod ;
	}
}
