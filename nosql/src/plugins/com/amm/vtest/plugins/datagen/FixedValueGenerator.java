package com.amm.vtest.plugins.datagen;

/**
 * Generates a fixed value based on key (default size is 1024).
 */
public class FixedValueGenerator implements ValueGenerator {				
	private int defaultSize = 1024 ;

	public FixedValueGenerator() {
	}

	public FixedValueGenerator(int size) {
		this.defaultSize = size;
	}

	public String getValue() {
		return DataGeneratorUtils.createString(defaultSize);
	}

	public String getValue(String key) {
		return DataGeneratorUtils.createString(defaultSize, key);
	}

	public String getValue(String key, int size) {
		return DataGeneratorUtils.createString(size, key);
	}

	public boolean canCheckValue() {
		return true ;
	}

	@Override
	public String toString() {
		return 
				"[defaultSize="+defaultSize
			+ "]"
			;
	}
}
