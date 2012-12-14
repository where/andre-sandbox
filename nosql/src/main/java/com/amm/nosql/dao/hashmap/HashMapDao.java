package com.amm.nosql.dao.hashmap;

import java.util.*;
import com.amm.nosql.data.NoSqlEntity;
import com.amm.nosql.dao.NoSqlDao;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap implementation - to be used for simple in-memory testing.
 * @author amesarovic
 */
public class HashMapDao<T extends NoSqlEntity> implements NoSqlDao<T> {
	private final Map<String,T> cache ;

	public HashMapDao() {
		cache = Collections.synchronizedMap(new HashMap<String,T>());
	}

	public HashMapDao(boolean useConcurrentHashMap) {
		cache = useConcurrentHashMap
			? new ConcurrentHashMap<String,T>()
			: Collections.synchronizedMap(new HashMap<String,T>());
	}

	public void put(T obj) throws Exception {
		String key = getKey(obj);
		cache.put(key, obj);
	}

	public T get(String id) throws Exception {
		T obj = cache.get(id);
		return obj;
	}

	public Map<String,T> getBulk(Collection<String> keys) throws Exception {
		Map<String,T> map = new HashMap<String,T>();
		for (String key : keys) {
			T obj = get(key);
			map.put(key,obj);
		}
		return map;
	}

	public void delete(String id) throws Exception {
		cache.remove(id);
	}

	private String getKey(T obj) {
		return obj.getKey().toString();
	}

	@Override 
	public String toString() {
		return 
			"cache.size="+cache.size()
			+" cache.class="+cache.getClass().getName();
	}
}
