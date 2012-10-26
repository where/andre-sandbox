package com.amm.nosql.data;

import java.util.*;

/**
 * Representation of a Key and Value.
 * @author andre
 */
public class KeyStringValue extends NoSqlEntity<String> {
	public KeyStringValue() {
	}

	public KeyStringValue(String key) {
		super(key);
	}

	public KeyStringValue(String key, String value) {
		super(key);
		this.value = value ;
	}

	private String value; 
	public String getValue() { return value; }
	public void setValue(String value) { this.value = value; } 

	@Override
	public String toString() {
		return
			"[key=" + key
			+value==null?" value=null":(" value.size=" + value.length())
			+"]"
	   ;
	}
}
