package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;
import com.andre.rest.data.PagedList;

@XmlRootElement(name="stores")
//public class StoreList extends PagedList {
public class StoreList {
	private List<Store> stores = new ArrayList<Store>();

	public StoreList() {
	}

	public StoreList(List<Store> stores) {
		this.stores = stores;
	}

	@XmlElement(name="store")
	public List<Store> getStores() {
		return stores;
	}

	public void add(Store store) {
		stores.add(store);
	}
}
