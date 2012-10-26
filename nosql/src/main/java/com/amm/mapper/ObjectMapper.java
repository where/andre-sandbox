package com.amm.mapper;

/**
 * Maps an object to/from byte [] format.
 * @author amesarovic
 */
public interface ObjectMapper<T> {

	/** Maps bytes to entity. */
	public T toObject(byte [] bytes) throws Exception ;

	/** Maps entity to bytes. */
	public byte [] toBytes(T object) throws Exception ;
}
