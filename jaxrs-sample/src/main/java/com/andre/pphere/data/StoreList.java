package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import com.andre.rest.data.PagedList;

@XmlRootElement(name="stores")
public class StoreList extends PagedList {
	private List<Store> stores;

	public StoreList() {
	}

	public StoreList(List<Store> stores) {
	  this.stores = stores;
	}

	@XmlElement(name="store")
	public List<Store> getStores() {
	  return stores;
	}
}
