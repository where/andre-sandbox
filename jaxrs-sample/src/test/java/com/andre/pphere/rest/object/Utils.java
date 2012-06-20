package com.andre.pphere.rest.object;

import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import com.andre.pphere.data.Store;
import com.andre.pphere.data.StoreList;
import com.andre.pphere.data.*;

/**
 */
public class Utils {

	public static StoreList createStoreList(String name) {
		StoreList list = new StoreList();
		list.add(createStore("ems"));
		list.add(createStore("rei"));
		return list;
	}

	public static Store createStore(String name) {
		Store store = new Store();
		store.setName(name);
		store.setAccountId("accountId");
		store.setPhoneNumber("617 123-1234");
		Location location = new Location();
		location.setStreet1("Main st");
		location.setStreet2("Test st");
		store.setLocation(location);
		return store;
	}
}
