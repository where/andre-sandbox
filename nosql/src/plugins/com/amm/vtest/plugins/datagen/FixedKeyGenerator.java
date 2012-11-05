package com.amm.vtest.plugins.datagen;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creates a fixed size 0-padded (36 characters default) key - incremented each time by getNextKey().
 */
public class FixedKeyGenerator implements KeyGenerator {				
	private final AtomicInteger index ;
	private int size = 36 ;
	private int start = 0 ;

	public FixedKeyGenerator() {
		index = new AtomicInteger(0);
	}

	public FixedKeyGenerator(boolean doPad) {
		index = new AtomicInteger(0);
		this.doPad = doPad ;
	}

	public FixedKeyGenerator(int size) {
		this.size = size ;
		index = new AtomicInteger(0);
	}

	public FixedKeyGenerator(int size, int start) {
		this.size = size ;
		this.start = start ;
		index = new AtomicInteger(start);
	}

	public void reset() {
		if (doReset)
			index.getAndSet(start);
	}

	public String getNextKey() {
		return doPad ?	KeyGeneratorUtils.createPaddedKey(index.getAndIncrement(), size) : ""+index.getAndIncrement();
	}
 
	private boolean doReset = true ;
	public boolean getDoReset() { return doReset; }
	public void setDoReset(boolean doReset) { this.doReset = doReset; } 

	private boolean doPad = true ;
	public boolean getDoPad() { return doPad; }
	public void setDoPad(boolean doPad) { this.doPad = doPad; } 

	@Override
	public String toString() {
		return 
				"[index="+index
			+ " size="+size
			+ " start="+start
			+ " doReset="+doReset
			+ " doPad="+doPad
			+ "]"
			;
	}
}
