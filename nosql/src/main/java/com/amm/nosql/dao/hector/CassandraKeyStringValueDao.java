package com.amm.nosql.dao.cassandra;

import java.util.*;
import org.apache.log4j.Logger;
import com.amm.nosql.dao.KeyStringValueDao;
import com.amm.nosql.data.KeyStringValue;
import com.amm.mapper.ObjectMapper;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.ColumnQuery;

/**
 * Maps the entire KeyStringValue object (should only be attributes) to one "blob" column (JSON or Java Serialized).
 * @author amesarovic
 */
public class CassandraKeyStringValueDao<X extends KeyStringValue> implements KeyStringValueDao {
	private static final Logger logger = Logger.getLogger(CassandraKeyStringValueDao.class);
	private static final String VALUE_COLUMN_NAME = "value";
	private String columnFamilyName;
	private Keyspace keyspace;
	private final StringSerializer serializerKey = StringSerializer.get();
	private final ObjectSerializer serializerValue = ObjectSerializer.get();
	private ObjectMapper<KeyStringValue> entityMapper;

	public CassandraKeyStringValueDao(Keyspace keyspace, String columnFamilyName, ObjectMapper<KeyStringValue> entityMapper) {
		this.keyspace = keyspace ;
		this.columnFamilyName = columnFamilyName ;
		this.entityMapper = entityMapper ;
		logger.debug("keyspace="+keyspace+" columnFamilyName="+columnFamilyName);
	}

	public KeyStringValue get(String key) throws Exception { 
		ColumnQuery<String, String, Object> query = HFactory.createColumnQuery(keyspace,
			serializerKey, serializerKey, serializerValue);
		query.setKey(key);
		query.setName(VALUE_COLUMN_NAME);
		query.setColumnFamily(columnFamilyName);
		QueryResult<HColumn<String, Object>> result = query.execute();
		HColumn<String, Object> column = result.get();
		logger.debug("key="+key+" columnName="+VALUE_COLUMN_NAME+" result="+column);
		if (column == null)
			return null ;

		byte [] value = (byte [])column.getValue();
		KeyStringValue keyValue = entityMapper.toObject(value);
		return keyValue ;
	}

	public Map<String,KeyStringValue> getBulk(Collection<String> keys) throws Exception {
		throw new UnsupportedOperationException();
	}
	public void put(KeyStringValue entity) throws Exception {
		byte [] value = entityMapper.toBytes(entity);
		HFactory.createMutator(keyspace, serializerKey).insert(
				entity.getKey(), columnFamilyName, HFactory.createColumn(VALUE_COLUMN_NAME, value, serializerKey, serializerValue));
	}

	public void delete(String key) throws Exception {
		Mutator<String> m = HFactory.createMutator(keyspace, serializerKey);
		m.addDeletion(key, columnFamilyName);
		m.execute();
	}

	@Override
	public String toString() {
		return
			"[keyspace="+keyspace.getKeyspaceName()
			+ " columnFamily="+columnFamilyName
			+ "]"
			;
	}
}
