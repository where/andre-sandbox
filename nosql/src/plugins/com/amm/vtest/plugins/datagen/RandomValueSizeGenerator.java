package com.amm.vtest.plugins.datagen;

import java.util.*;

/**
 * Generates a random value with a random size bounded by baseSize and range.
 */
public class RandomValueSizeGenerator extends RandomValueGenerator {				
	private int baseSize ;
	private int range ;

	public RandomValueSizeGenerator(long seed, int baseSize, int range) {
		super(baseSize, seed);
		this.baseSize = baseSize ;
		this.range = range ;
	}

	public String getValue() {
		return createRandomLetters(getNextSize());
	}

	synchronized
	private int getNextSize() {
		int size = getRandom().nextInt(range) + baseSize ;
		//System.out.println("==>> RandomValueSizeGenerator: size="+size+" baseSize="+baseSize);
		return size ;
	}

	public int getBaseSize() { return baseSize ; }
	public int getRange() { return range ; }
	public Long getRandomSeed() { return getSeed() ; }

	@Override
	public String toString() {
		return
			"[baseSize="+baseSize
			+ " range="+range
			+ " seed="+getSeed()
			+ " class="+this.getClass().getName()
			+ "]"
			;
	}
}
