package com.paypal.ppmn.rps.vtest;

import com.paypal.ppmn.rps.client.ProfileClient;
import com.paypal.ppmn.rps.data.Profile;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;

public class GetTask extends ProfileBaseTask {
	private static final Logger logger = Logger.getLogger(GetTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private boolean debug = logger.isDebugEnabled();
	private String name = "Get";

	public GetTask(ProfileClient client, MyTaskConfig config, String name) throws Exception {
		super(client,config);
		this.name = name ;
	}
	public GetTask(ProfileClient client, MyTaskConfig config) throws Exception {
		super(client,config);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		String key = getNextKey();

		Profile profile = client.get(key);
		logger.debug("count="+count+" key="+key+" profile=["+profile+"]");

		count.getAndIncrement();
	}

	public String getName() { return name ; }
}
