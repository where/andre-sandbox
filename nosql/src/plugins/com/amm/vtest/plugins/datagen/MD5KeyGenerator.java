package com.amm.vtest.plugins.datagen;

import java.util.*;
import java.security.NoSuchAlgorithmException;
import org.apache.log4j.Logger;

/**
 * Generates a MD5 key.
 */
public class MD5KeyGenerator implements KeyGenerator {				
	private static final Logger logger = Logger.getLogger(MD5KeyGenerator.class);
	private Random random ;
	private Long seed ;
	private int cycleMax = 0 ;
	private int cycleIndex = 0 ;
	private boolean doReset = true ;
	private String algorithm = "MD5" ;

	public MD5KeyGenerator() {
		random = new Random();
		logger.debug("algorithm="+algorithm);
	}

	public MD5KeyGenerator(String algorithm) {
		this.algorithm = algorithm ;
		random = new Random();
		logger.debug("algorithm="+algorithm);
	}

	public MD5KeyGenerator(long seed) {
		random = new Random(seed);
		this.seed = seed ;
		random = new Random(seed);
		logger.debug("algorithm="+algorithm+" seed="+seed);
	}

	public String getNextKey() {
		if (cycleMax > 0)
			checkCycle();
		int rnum = random.nextInt();

		try {
			String signature = MD5Utils.createSignature(""+rnum);
			return signature;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public void reset() {
		if (doReset)
			random = (seed == null) ?	new Random() : new Random(seed);
	}

	private synchronized void checkCycle() {
		 if (cycleIndex > cycleMax) {
			 reset();
			 cycleIndex = 0 ;
		 } else
			 cycleIndex++;
	}

	public void setCycleMax(int cycleMax) { this.cycleMax = cycleMax;}
	public void setDoReset(boolean doReset) { this.doReset = doReset;}

	public Long getRandomSeed() { return seed ; }
	public int getCycleMax() { return cycleMax ; }
	public int getCycleIndex() { return cycleIndex ; }
	public boolean getDoReset() { return doReset ; }

	@Override
	public String toString() {
		return
			"[class="+this.getClass().getName()
			+ " seed="+seed
			+ " cycleMax="+cycleMax
			+ " doReset="+doReset
			+ "]"
			;
	}
}
