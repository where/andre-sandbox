package com.amm.nosql.data;

import java.util.*;

/**
 * Representation of a Key and Value.
 * @author andre
 */
public class KeyValue extends NoSqlEntity<String> {
	public KeyValue() {
	}

	public KeyValue(String key) {
		super(key);
	}

	public KeyValue(String key, byte [] value) {
		super(key);
		this.value = value ;
	}

	private byte[] value; 
	public byte[] getValue() { return value; }
	public void setValue(byte[] value) { this.value = value; } 

	@Override
	public String toString() {
		return
			"[key=" + key
			+value==null?" value=null":(" value.length=" + value.length)
			+"]"
	   ;
	}
}
