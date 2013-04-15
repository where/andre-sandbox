package com.amm.nosql.dao.cassandra.astyanax;

import java.util.*;
import java.nio.ByteBuffer;
import com.amm.mapper.ObjectMapper;
import com.amm.nosql.NoSqlException;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.MutationBatch;
import com.netflix.astyanax.thrift.ThriftFamilyFactory;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.OperationResult;
import com.netflix.astyanax.serializers.StringSerializer;
import com.netflix.astyanax.serializers.ObjectSerializer;
import com.netflix.astyanax.serializers.BytesArraySerializer;
import com.netflix.astyanax.serializers.ByteBufferSerializer;
import com.netflix.astyanax.model.Column;
import com.netflix.astyanax.model.ColumnFamily;
import com.netflix.astyanax.model.ColumnList;
import com.netflix.astyanax.ColumnListMutation;
import com.amm.nosql.data.KeyValue;
import com.amm.nosql.dao.KeyValueDao;

/**
 * Astyanax implementation. 
 * @author amesarovic
 */
public class AstyanaxKeyValueStringDao<X extends KeyValue> implements KeyValueDao {
	private Keyspace keyspace ;
	private	ColumnFamily<String, String> columnFamily ;
	private final String columnFamilyName ; 
	private final String clusterName ; 
	private final String keyspaceName ; 
	private final String seedUrl ; 
	private final int port ; 
	private Integer ttl = null;
	private final ObjectMapper<KeyValue> objectMapper ;
	private static final String VALUE_COLUMN_NAME = "value";
	private final String connectionPool = "MyConnectionPool" ;

	public AstyanaxKeyValueStringDao(String clusterName, String keyspaceName, String columnFamilyName, String seedUrl, int port, ObjectMapper<KeyValue> objectMapper) {
		this.clusterName = clusterName ;
		this.keyspaceName = keyspaceName ;
		this.columnFamilyName = columnFamilyName ;
		this.seedUrl = seedUrl ;
		this.port = port ;
		this.objectMapper = objectMapper ;
		init();
	}

	public KeyValue get(String key) throws Exception {
		OperationResult<ColumnList<String>> result =
			keyspace.prepareQuery(columnFamily)
				.getKey(key)
				.execute();
		//debug("get: result: "+result);
		//debug("get: result: Latency="+result.getLatency());
		//debug("get: result: Host="+result.getHost());
		ColumnList<String> columns = result.getResult();

		Column<String> column = columns.getColumnByName(VALUE_COLUMN_NAME);
		//debug("get: column="+column);

		if (column == null)
			return null ;
		byte [] value = column.getStringValue().getBytes();
		KeyValue keyValue = objectMapper.toObject(value);
		keyValue.setKey(key);
		return keyValue ;
	}

	public void put(KeyValue entity) throws Exception {
		MutationBatch m = keyspace.prepareMutationBatch();
 		ColumnListMutation<String> clm = m.withRow(columnFamily, ""+entity.getKey());
		byte [] value = objectMapper.toBytes(entity);
		clm.putColumn(VALUE_COLUMN_NAME, value, ttl);

  		OperationResult<Void> result = m.execute();

		//debug("put: result: "+result);
		//debug("put: result: latency="+result.getLatency());
	}

	public void delete(String key) throws Exception {
	}

	void init() {
		AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
    		.forCluster(clusterName)
    		.forKeyspace(keyspaceName)
    		.withAstyanaxConfiguration(new AstyanaxConfigurationImpl()      
        		.setDiscoveryType(NodeDiscoveryType.NONE)
        		//.setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
    		)
    		.withConnectionPoolConfiguration(new ConnectionPoolConfigurationImpl(connectionPool)
        		.setPort(port)
        		.setMaxConnsPerHost(1)
        		.setSeeds(seedUrl)
    		)
    		.withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
    		.buildKeyspace(ThriftFamilyFactory.getInstance());

		context.start();
		keyspace = context.getEntity();

		columnFamily =
  			new ColumnFamily<String, String>(
    			columnFamilyName,             // Column Family Name
    			StringSerializer.get(),       // Key Serializer
    			StringSerializer.get());  // Column Serializer
	}

    public Map<String,KeyValue> getBulk(Collection<String> keys) throws Exception {
		throw new UnsupportedOperationException();
	}

	void debug(Object o) { System.out.println(">> Astyanax: "+o);}
}
