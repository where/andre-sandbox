package com.amm.nosql.vtest;

import java.util.*;
import com.amm.vtest.BaseTask;
import org.apache.log4j.Logger;

import com.amm.vtest.Task;
import com.amm.vtest.TaskConfig;
import com.amm.vtest.plugins.datagen.KeyGenerator;
import com.amm.vtest.plugins.datagen.ValueGenerator;

/**
 * Base NoSQL task.
 */
abstract public class NoSqlTask extends BaseTask {
	private static final Logger logger = Logger.getLogger(NoSqlTask.class);
	private NoSqlTaskConfig config ;
	private ValueGenerator valueGenerator ;
	private KeyGenerator keyGenerator ;

	public NoSqlTask(NoSqlTaskConfig config) throws Exception {
		this.config = config ;
		this.valueGenerator = config.getValueGenerator();;
		this.keyGenerator = config.getKeyGenerator();;
		//this.checkValue = config.getCheckValue();;
		//this.checkValue = valueGenerator.canCheckValue();;

		logger.debug("keyGenerator="+keyGenerator);
		logger.debug("keyGenerator.class="+keyGenerator.getClass().getName());
		logger.debug("valueGenerator.class="+valueGenerator.getClass().getName());
	}

	public void init() throws Exception {
		keyGenerator.reset();
	}

	public TaskConfig getTaskConfig() { return config ; }

	public KeyGenerator getKeyGenerator() { 
		return keyGenerator; 
	}
	public void setKeyGenerator(KeyGenerator keyGenerator) { 
	 this.keyGenerator = keyGenerator; 
	} 

	public String getNextKey() { 
		return keyGenerator.getNextKey(); 
	}

	public ValueGenerator getValueGenerator() { 
		return valueGenerator; 
	}
	public String getValue(String key) { 
		return valueGenerator.getValue(key, config.getValueSize()); 
	}
	public String getValue() { 
		return valueGenerator.getValue() ;
	}

	public void setValueSeed(int val) { 
		//valueGenerator.setSeed(val); TODO
	}

	private String name;
	public String getName() { return name==null?getClass().getSimpleName():name; }
	public void setName(String name) { this.name = name; }

	public boolean getCheckValue() { return valueGenerator.canCheckValue(); }
}
