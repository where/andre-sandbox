package com.amm.nosql.vtest;

import com.amm.vtest.BaseTask;
import com.amm.vtest.TaskConfig;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.amm.vtest.plugins.datagen.ValueGenerator;
import com.amm.vtest.plugins.datagen.FixedValueGenerator;
import com.amm.vtest.plugins.datagen.KeyGenerator;
import com.amm.vtest.plugins.datagen.FixedKeyGenerator;

public class NoSqlTaskConfig extends TaskConfig {
	private int valueSize = 1 ;
	public int getValueSize() { return valueSize; }
	public void setValueSize(int valueSize) { this.valueSize = valueSize; } 

	private ValueGenerator valueGenerator = new FixedValueGenerator();
	public ValueGenerator getValueGenerator() { return valueGenerator; }
	public void setValueGenerator(ValueGenerator valueGenerator) { this.valueGenerator = valueGenerator; } 

	private KeyGenerator keyGenerator = new FixedKeyGenerator();
	public KeyGenerator getKeyGenerator() { return keyGenerator; }
	public void setKeyGenerator(KeyGenerator keyGenerator) { this.keyGenerator = keyGenerator; } 

	@Override
	public String toString() {
		return
			"valueSize=" + valueSize 
			+ " valueGenerator=" + valueGenerator.getClass().getName()
			+ " keyGenerator=" + keyGenerator.getClass().getName()
		;
	}
}
