package com.amm.nosql.vtest.profile;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;
import com.amm.nosql.data.Profile;
import com.amm.vtest.TaskConfig;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;

public class GetTask extends ProfileTask {
	private static final Logger logger = Logger.getLogger(GetTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private boolean checkValue ;
	private boolean checkSize ;

	public GetTask(ProfileTaskConfig config) throws Exception {
		super(config);
		setName("Get");
	}

	public GetTask(ProfileTaskConfig config, String name) throws Exception {
		super(config);
		setName(name);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		try {
			String key = getNextKey();
			String svalue = getValue(key);
			logger.debug("count="+count+" key="+key+" #svalue="+svalue.length());
			long timestamp = System.currentTimeMillis();
	
			Profile profile = getProfileDao().get(key);
			if (profile == null) 
				throw new FailureException("get: No value for key="+key+" count="+count);
			if (checkValue) {
				Map<String, String> attrs = profile.getAttributes();
				if (attrs == null || attrs.size() == 0)
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
