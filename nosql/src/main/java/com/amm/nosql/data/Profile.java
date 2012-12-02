package com.amm.nosql.data;

import java.util.*;

/**
 * Profile represents basic information for a user.
 * @author amesarovic
 */
public class Profile extends NoSqlEntity<String> {

	private Map<String, String> attributes = new HashMap<String, String>();

	public Profile() {
	}

	public Profile(String id) {
		super(id);
	}

	public Profile(String id, Map<String, String> attributes) {
		super(id);
		this.attributes = attributes ;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
 
	public void addAttribute(String key, String value) {
		attributes.put(key, value);
	}

	private Date timestamp; 
	public Date getTimestamp() { return timestamp; }
	public void setTimestamp(Date timestamp) { this.timestamp = timestamp; } 

	@Override
	public String toString() {
		//StringBuilder sbuf = new StringBuilder("[");
		int nbytes = 0 ;
		if (attributes != null) {
			for (Map.Entry<String,String> entry : attributes.entrySet()) {
				//sbuf.append(entry.getKey()+"="+entry.getValue()+"'");
				nbytes += entry.getValue().length();
			}
		}
		//sbuf.append("]");
		return
			"[key=" + getKey()
			+ " timestamp=["+timestamp+"]"
			//+ (timestamp==null?"": " timestamp="+timestamp.getTime())
			+" #attributes=" + attributes.size()
			+" nbytes="+nbytes
			//+" attributes=" + sbuf
			+"]"
	   ;
	}
}
