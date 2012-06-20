package com.andre.pphere.dao.mock;

import org.apache.log4j.Logger;
import com.andre.pphere.dao.StoreDao;
import com.andre.pphere.data.*;
import java.util.*;

/**
 * Mock Store DAO.
 */
public class MockStoreDao implements StoreDao {
	private static final Logger logger = Logger.getLogger(MockStoreDao.class);
	private Map<Long,Store> stores = new LinkedHashMap<Long,Store>();
	private long currentStoreId = 0 ;

	public MockStoreDao(List<Store> lstores) {
		for (Store store: lstores) {
			store.setId(currentStoreId);
			stores.put(currentStoreId++, store);
		}
	}

	private Long getStoreId(String name) {
		for (Map.Entry<Long,Store> entry : stores.entrySet()) {
			Store store = entry.getValue();
			if (name.equals(store.getName())) {
				return entry.getKey();
			}
		}
		return null;
	}


	public MockStoreDao(int numStores) {
		createStores(numStores) ;
	}

	private void createStores(int numStores) {
		for (int j=0 ; j < numStores ; j++) {
			Store store = new Store();
			store.setId((long)j);
			store.setName("Store"+j);
			store.setPhoneNumber("123-1234");
			store.setProviderStoreId("provStoreId");
			store.setAccountId("AccountId"+j);
			Location location = new Location();
			stores.put((long)j,store);
		}
		currentStoreId = numStores ;
		logger.debug("numStores="+numStores+" currentStoreId="+currentStoreId);
	}


	public List<Store> getStores() {
		List<Store> list = new ArrayList<Store>(stores.values());
		return list;
	}

	public List<Store> getStoresByName(String name) {
		List<Store> list = new ArrayList<Store>();
		for (Store store : stores.values()) {
			if (store.getName().startsWith(name))
				list.add(store);
		}
		return list;
	}

	public Store getStore(long id) {
		return stores.get(id);
	}

	public long createStore(Store store) {
		logger.debug(": currentStoreId="+currentStoreId);
		store.setId(currentStoreId);
		stores.put(currentStoreId, store);
		long oldId = currentStoreId ;
		currentStoreId++;
		logger.debug("currentStoreId="+currentStoreId+" oldId="+oldId);
		return oldId;
	}

	public void updateStore(Store store) {
		logger.debug("store="+store);
		stores.put(store.getId(), store);
	}

	public void deleteStore(long id) {
		stores.remove(id);
	}
}
