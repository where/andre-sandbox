package com.amm.nosql.mapper;

import java.util.*;
import com.amm.mapper.ObjectMapper;
import com.amm.nosql.data.KeyValue;

/**
 * Maps KeyValue entity.
 * @author amesarovic
 */
public class KeyValueMapper implements ObjectMapper<KeyValue> {

	public KeyValue toObject(byte [] value) throws Exception {
		KeyValue entity = new KeyValue();
		entity.setValue(value);
		return entity;
	}

	public byte [] toBytes(KeyValue entity) throws Exception {
		return entity.getValue();
	}
}
