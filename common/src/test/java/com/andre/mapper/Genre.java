package com.andre.mapper;

import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "genre")
@XmlType(name = "Genre")

public class Genre implements java.io.Serializable {

	public Genre() {
	}

	public Genre(String name, long id) {
		this.name = name ;
		this.id = id ;
	}

	private String name; 
	public String getName() { return name; }
	public void setName(String name) { this.name = name; } 

	private long id; 
	public long getId() { return id; }
	public void setId(long id) { this.id = id; } 
 
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true ;
		if ( !(obj instanceof Genre) ) return false;
		Genre bean = (Genre) obj;

		if (name==null && bean.name==null) {
		} else if (name.length() != name.length()) {
			return false ;
		} else {
			if (!name.equals(bean.name)) return false ;
		}

		if (id != bean.id) return false ; 
		
		return true;
	}
}
