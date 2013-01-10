package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.*;
import com.andre.rest.data.PagedList;

@XmlRootElement(name="stores")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "StoreList", propOrder = {
	"count",
	"links",
	"stores",
})  
public class StoreList extends PagedList<Store> {
	
	public StoreList() {
	}
		
	public StoreList(List<Store> stores) {
		super(stores);
	}
	
	@XmlElement(name="store")
	public List<Store> getStores() { 
		return getList();
	}
} 

