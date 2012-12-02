package com.amm.nosql.dao.citrusleaf;

import net.citrusleaf.CitrusleafClient;
import com.amm.nosql.dao.KeyValueDao;
import com.amm.nosql.data.KeyValue;
import com.amm.mapper.ObjectMapper;

public class CitrusleafKeyValueDao extends CitrusleafDao<KeyValue> implements KeyValueDao {
    public CitrusleafKeyValueDao(String hostnames, String namespace, String set, String bin, ObjectMapper<KeyValue> mapper) throws Exception {
		super(hostnames, namespace, set, bin, mapper);
	}
    public CitrusleafKeyValueDao(CitrusleafClient client, String namespace, String set, String bin, ObjectMapper<KeyValue> mapper) throws Exception {
		super(client, namespace, set, bin, mapper);
	}
}
