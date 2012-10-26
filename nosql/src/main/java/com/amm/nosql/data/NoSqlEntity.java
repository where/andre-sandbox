package com.amm.nosql.data;

/**
 * Base object for NoSQL objects.
 * @author amesarovic
 */
public abstract class NoSqlEntity<K> { 
	public NoSqlEntity() {
	}

	public NoSqlEntity(K key) {
		this.key = key;
	}

	protected K key;

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		return "key="+key ;
	}
}
