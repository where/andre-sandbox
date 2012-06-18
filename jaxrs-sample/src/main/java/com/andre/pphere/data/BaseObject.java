package com.andre.pphere.data;

import java.util.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Ye old base data object class.
 */
@XmlTransient
public class BaseObject {
	protected Integer id;
	@XmlElement(required=true)
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id=id; } 
 
	public BaseObject() {}

	public BaseObject(Integer id) {
		this.id = id ;
	}

	@Override
	public String toString() {
	return
		 "id="+id
		 ;
	}
}
