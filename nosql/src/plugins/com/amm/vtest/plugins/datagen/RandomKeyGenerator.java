package com.amm.vtest.plugins.datagen;

import org.apache.log4j.Logger;

/**
 * Generates a random key.
 */
public class RandomKeyGenerator implements KeyGenerator {				
	private static final Logger logger = Logger.getLogger(RandomKeyGenerator.class);
	private RandomValueGenerator randomValueGenerator ;
	private int size = 36 ;
	private Long seed ;
	private int cycleMax = 0 ;
	private int cycleIndex = 0 ;
	private boolean doReset = true ;

	public RandomKeyGenerator() {
		randomValueGenerator = new RandomValueGenerator(size);
		logger.debug("randomValueGenerator="+randomValueGenerator);
	}

	public RandomKeyGenerator(int size) {
		this.size = size ;
		randomValueGenerator = new RandomValueGenerator(size);
		logger.debug("randomValueGenerator="+randomValueGenerator);
	}

	public RandomKeyGenerator(int size, long seed) {
		this.size = size ;
		this.seed = seed ;
		randomValueGenerator = new RandomValueGenerator(size, seed);
		logger.debug("randomValueGenerator="+randomValueGenerator);
	}

	public synchronized String getNextKey() {
		if (cycleMax > 0)
			checkCycle();
		return randomValueGenerator.getValue("");
	}

	public synchronized void reset() {
		if (doReset)
			randomValueGenerator = (seed == null) ?	new RandomValueGenerator(size) : new RandomValueGenerator(size, seed);
	}

	private void checkCycle() {
		 if (cycleIndex > cycleMax) {
			 reset();
			 cycleIndex = 0 ;
		 } else {
			 cycleIndex++;
		}
	}

	public void setCycleMax(int cycleMax) { this.cycleMax = cycleMax;}
	public void setDoReset(boolean doReset) { this.doReset = doReset;}
	public void setSeed(long seed) { randomValueGenerator.setSeed(seed); }

	@Override
	public String toString()
	{
		return
			"[randomValueGenerator="+randomValueGenerator
			+ " class="+this.getClass().getName()
			+ " size="+size
			+ " seed="+seed
			+ " cycleMax="+cycleMax
			+ " doReset="+doReset
			+ "]"
			;
	}
}
