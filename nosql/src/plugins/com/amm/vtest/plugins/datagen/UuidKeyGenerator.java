package com.amm.vtest.plugins.datagen;

/**
 * Generates a 36 character UUID key.
 */
public class UuidKeyGenerator implements KeyGenerator {				
	public String getNextKey() {
		return KeyGeneratorUtils.createUuidKey();
	}

	public void reset() {
	}
}
