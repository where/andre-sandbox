package com.paypal.ppmn.rps.vtest;

import com.paypal.ppmn.rps.client.ProfileClient;
import com.paypal.ppmn.rps.data.Profile;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;

public class PingTask extends ProfileBaseTask {
	private static final Logger logger = Logger.getLogger(PingTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private String name = "Ping";

	public PingTask(ProfileClient client, MyTaskConfig config) throws Exception {
		super(client,config);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		client.ping();
		count.getAndIncrement();
	}

	public String getName() { return name ; }
}
