package com.andre.pphere.data;

import java.util.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Ye old base data object class.
 */
@XmlTransient
public class BaseObject {
	protected Long id;
	@XmlElement(required=false)
	public Long getId() { return id; }
	public void setId(Long id) { this.id=id; } 
 
	public BaseObject() {}

	public BaseObject(Long id) {
		this.id = id ;
	}

	@Override
	public String toString() {
	return
		 "id="+id
		 ;
	}
}
