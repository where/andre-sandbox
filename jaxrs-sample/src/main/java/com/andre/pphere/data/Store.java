package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "store")
@XmlType(name = "Store", propOrder = {
	"storeId",
	"name",
	"location",
	"phoneNumber",
	"providerStoreId",
	"accountId",
	"id"
})
public class Store extends BaseObject {
	public Store() {} 

	public Store(Long id) {
		setId(id);
	}

	private Long storeId; 
	@XmlElement(required=false)
	public Long getStoreId() { return storeId; }
	public void setStoreId(Long storeId) { this.storeId = storeId; } 

	private String name=null; 
	@XmlElement(required=true)
	public String getName() { return name; }
	public void setName(String val) { name=val; } 
 
	private Location location = new Location();
	@XmlElement(required=false)
	public Location getLocation() { return location; }
	public void setLocation(Location location) { this.location = location; } 
 
	private String phoneNumber; 
	@XmlElement(required=false)
	public String getPhoneNumber() { return phoneNumber; }
	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; } 
 
	private String providerStoreId; 
	@XmlElement(required=false)
	public String getProviderStoreId() { return providerStoreId; }
	public void setProviderStoreId(String providerStoreId) { this.providerStoreId = providerStoreId; } 
 
	private String accountId; 
	@XmlElement(required=false)
	public String getAccountId() { return accountId; }
	public void setAccountId(String accountId) { this.accountId = accountId; } 

	@Override
	public String toString() { 
		return
			"id="+getId()
			+ " name="+name
			+ " storeId="+storeId
			+ " phoneNumber="+phoneNumber
			;
	}
}
