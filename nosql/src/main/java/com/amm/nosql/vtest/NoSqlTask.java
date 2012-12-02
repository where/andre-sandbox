package com.amm.nosql.vtest;

import java.util.*;
import com.amm.vtest.BaseTask;
import org.apache.log4j.Logger;

import com.amm.vtest.Task;
import com.amm.vtest.TaskConfig;
import com.amm.vtest.plugins.datagen.KeyGenerator;
import com.amm.vtest.plugins.datagen.ValueGenerator;
import com.amm.vtest.plugins.datagen.RandomValueGenerator;

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
	public void setValueGenerator(ValueGenerator valueGenerator) { 
		this.valueGenerator = valueGenerator; 
	} 
	public String getValue(String key) { 
		return valueGenerator.getValue(key, config.getValueSize()); 
	}
	public String getValue() { 
		return valueGenerator.getValue() ;
	}

	public boolean getCheckValue() { 
		return valueGenerator.canCheckValue(); 
	}

	@Override
	public String toString() {
		return 
			super.toString()
			+" keyGenerator.class="+keyGenerator.getClass().getName()
			+" keyGenerator=["+keyGenerator+"]"
			+" valueGenerator.class="+valueGenerator.getClass().getName()
			+" valueGenerator=["+valueGenerator+"]"
		;
	}
}
