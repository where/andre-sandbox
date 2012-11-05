package com.amm.vtest.plugins.datagen;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates a random value.
 */
public class RandomValueGenerator implements ValueGenerator {				
	private final AtomicLong count = new AtomicLong();
	private int defaultSize = 1024 ;
	private Long seed ;
	private Random random ;

	public RandomValueGenerator() {
		random = new Random();
	}

	public RandomValueGenerator(int defaultSize) {
		this.defaultSize = defaultSize ;
		random = new Random();
	}

	public RandomValueGenerator(int defaultSize, long seed) {
		this.seed = seed ;
		this.defaultSize = defaultSize ;
		random = seed==-1 ? new Random() : new Random(seed);
	}

	public String getValue() {
		return createRandomLetters(defaultSize);
	}

	public String getValue(String key) {
		return createRandomLetters(defaultSize);
	}

	public String getValue(String key, int size) {
		return createRandomLetters(size);
	}

	public boolean canCheckValue() {
		return false ;
	}

	private static final String DIGITS = "0123456789";
	private static final String LETTERS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
	private static final String CHARACTERS = LETTERS + DIGITS + "~!@#$%^&*()____+-=[];',,,./>?:{}";

	protected String createRandomLetters(int length) {
		count.getAndIncrement();
		return randomString(LETTERS, length);
	}

	synchronized
	protected String randomString(String sampler, int length) {
		StringBuilder sbuf = new StringBuilder(length);
		for(int i = 0; i < length; i++)
			sbuf.append(sampler.charAt(random.nextInt(sampler.length())));
		return sbuf.toString();
	}

	public Random getRandom() { return random ; }

	public Long getSeed() { return seed ; }
	public void setSeed(long seed) { 
		this.seed = seed;
		random.setSeed(seed);
	}

	public Long getCount() { return count.get() ; }

	@Override
	public String toString() {
		return
			"[defaultSize="+defaultSize
			+ " seed="+seed
			+ " class="+this.getClass().getName()
			+ "]"
			;
	}
}
