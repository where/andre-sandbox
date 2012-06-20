package com.andre.pphere.dao;

import com.andre.pphere.data.*;
import java.util.*;

/**
 * Store DAO.
 */
public interface StoreDao {
	public List<Store> getStores() ;
	public Store getStore(long id) ;
	public long createStore(Store store);
	public void updateStore(Store store);
	public void deleteStore(long id);
}
