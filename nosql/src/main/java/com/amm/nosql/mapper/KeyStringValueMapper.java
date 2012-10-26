package com.amm.nosql.mapper;

import java.util.*;
import com.amm.mapper.ObjectMapper;
import com.amm.nosql.data.KeyStringValue;

/**
 * Maps KeyStringValue entity.
 * @author amesarovic
 */
public class KeyStringValueMapper implements ObjectMapper<KeyStringValue> {

	public KeyStringValue toObject(byte[]  value) throws Exception {
		KeyStringValue entity = new KeyStringValue();
		entity.setValue(new String(value));
		return entity;
	}

	public byte [] toBytes(KeyStringValue entity) throws Exception {
		return entity.getValue().getBytes();
	}
}
