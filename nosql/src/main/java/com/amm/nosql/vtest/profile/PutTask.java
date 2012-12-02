package com.amm.nosql.vtest.profile;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;
import com.amm.nosql.data.Profile;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;

public class PutTask extends ProfileTask {
	private static final Logger logger = Logger.getLogger(PutTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private boolean debug = logger.isDebugEnabled();
	private int numAttrs = 9 ;

	public PutTask(ProfileTaskConfig config) throws Exception {
		super(config);
		setName("Put");
	}

	public PutTask(ProfileTaskConfig config, String name) throws Exception {
		super(config);
		setName(name);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		try {
			String key = getNextKey();
			byte [] value = getValue().getBytes();


			Map<String, String> attrs = new HashMap<String, String>();
			for (int j=0 ; j < numAttrs ; j++) {
				String akey = new String(value);
				String avalue = new String(value);
				attrs.put(akey,avalue);
			}

			Profile keyValue = new Profile(key,attrs);
			if (debug)
				logger.debug("count="+count + " keySize="+key.length() +" valueSize="+value.length
					+" key="+key
					+" value="+new String(value)
					);

			getProfileDao().put(keyValue);
		} finally {
			count.getAndIncrement();
		}
	}
}
