package com.amm.vtest.plugins.datagen;

/**
 * Generates a value.
 */
public interface ValueGenerator {				

	public String getValue();

	public String getValue(String key);

	public String getValue(String key, int size);

	public boolean canCheckValue() ;
}
