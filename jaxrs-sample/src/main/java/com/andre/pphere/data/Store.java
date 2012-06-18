package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "store")
@XmlType(name = "Store", propOrder = {
	"name",
	"description",
	"id"
})
public class Store extends BaseObject {
	public Store() {} 

	public Store(Integer id) {
		setId(id);
	}
	public Store(Integer id, String name) {
		setId(id);
		this.name = name ;
	}

	private String name=null; 
	@XmlElement(required=true)
	public String getName() { return name; }
	public void setName(String val) { name=val; } 
 
	private String description=null; 
	@XmlElement(required=false)
	public String getDescription() { return description; }
	public void setDescription(String val) { description=val; } 

	@Override
	public String toString() { 
		return
			"id="+getId()
			+ " name="+name
			+ " description="+description
			;
	}
}
