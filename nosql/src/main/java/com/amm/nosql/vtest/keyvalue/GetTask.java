package com.amm.nosql.vtest.keyvalue;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;
import com.amm.nosql.data.KeyValue;
import com.amm.vtest.TaskConfig;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;

public class GetTask extends KeyValueTask {
	private static final Logger logger = Logger.getLogger(GetTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private boolean checkValue ;
	private boolean checkSize ;

	public GetTask(KeyValueTaskConfig config) throws Exception {
		super(config);
		setName("Get");
	}

	public GetTask(KeyValueTaskConfig config, String name) throws Exception {
		super(config);
		setName(name);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		try {
			String key = getNextKey();
			String svalue = getValue(key);
			logger.debug("count="+count+" key="+key+" #svalue="+svalue.length());
			long timestamp = System.currentTimeMillis();
	
			KeyValue keyValue = getKeyValueDao().get(key);
			if (keyValue == null) 
				throw new FailureException("get: No value for key="+key+" count="+count);
			if (checkValue) {
				byte [] value = keyValue.getValue();
				if (value == null || value.length == 0)
					throw new FailureException("get: No value for key="+key+" count="+count);
			}
		}
		finally {
			count.getAndIncrement();
		}
	}

	public void setCheckValue(boolean checkValue) { this.checkValue = checkValue ; }
	public void setCheckSize(boolean checkSize) { this.checkSize = checkSize ; }

	@Override
	public String toString() {
		return
			"checkValue="+checkValue
			+ " checkSize="+checkSize
			;
	}   
}
