package com.paypal.ppmn.rps.vtest;

import com.paypal.ppmn.rps.client.ProfileClient;
import com.paypal.ppmn.rps.data.Profile;
import com.amm.vtest.FailureException;
import com.amm.vtest.services.callstats.CallStats;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.log4j.Logger;

public class PutTask extends ProfileBaseTask {
	private static final Logger logger = Logger.getLogger(PutTask.class);
	private volatile AtomicLong count = new AtomicLong();
	private boolean debug = logger.isDebugEnabled();
	private String name = "Put";

	public PutTask(ProfileClient client, MyTaskConfig config, String name) throws Exception {
		super(client,config);
		this.name = name ;
	}
	public PutTask(ProfileClient client, MyTaskConfig config) throws Exception {
		super(client,config);
	}

	public void execute(CallStats stats) throws FailureException, Exception {
		String key = getNextKey();
		String value = getValue() ;

		//if (debug)
		logger.debug("count="+count+" key="+key+" key.size="+key.length()+" value="+value);
		Profile profile = newProfile(key,value);
		client.save(profile);
		count.getAndIncrement();
	}

	public String getName() { return name ; }

    public Profile newProfile(String key, String value) {
        Profile profile = new Profile(key);
        String provider = "Provider";
        String dt = "Dt";
        String field = "Field";
        profile.add(provider, dt, field, value);
        profile.add(provider, dt+"2", field+"2", value+"2");
        return profile ;
    }
}
