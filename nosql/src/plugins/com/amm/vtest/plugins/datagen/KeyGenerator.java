package com.amm.vtest.plugins.datagen;

/**
 * Generates a key.
 */
public interface KeyGenerator {				
	public String getNextKey();
	public void reset();
}
