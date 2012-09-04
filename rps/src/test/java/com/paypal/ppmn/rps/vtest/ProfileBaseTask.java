package com.paypal.ppmn.rps.vtest;

import com.paypal.ppmn.rps.client.ProfileClient;
import java.util.*;
import org.apache.log4j.Logger;
import com.amm.vtest.BaseTask;
import com.amm.vtest.services.datagen.KeyGenerator;
import com.amm.vtest.services.datagen.ValueGenerator;

abstract public class ProfileBaseTask extends BaseTask {
	private static final Logger logger = Logger.getLogger(ProfileBaseTask.class);
	private MyTaskConfig config ;
	ProfileClient client ;

	public ProfileBaseTask(ProfileClient client, MyTaskConfig config) throws Exception {
		this.client = client ;
		this.config = config ;
		//logger.debug("keyValueDao=["+keyValueDao+"] keyGenerator="+getKeyGenerator()+" valueGenerator="+getValueGenerator());
	}

	public void init() {
	}

    public String getNextKey() {
		return config.getKeyGenerator().getNextKey();
    }

    public ValueGenerator getValueGenerator() {
        return config.getValueGenerator();
    }
    public String getValue(String key) {
        return config.getValueGenerator().getValue(key, config.getValueSize());
    }
    public String getValue() {
        return config.getValueGenerator().getValue();
    }
}
