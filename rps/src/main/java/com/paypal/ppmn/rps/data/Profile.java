package com.paypal.ppmn.rps.data;

import java.util.*;

public class Profile {
	public String id;
	//public String cd;
	//public String ad;
	//public Map<String,Map<String,Entry>> providers = new HashMap<String,Map<String,Entry>>();


	public Profile() {
	}

	public Profile(String id) {
		this.id = id;
		//this.cd = ""+System.currentTimeMillis();
		//this.cd = ""+new Date();
	}

	public void addId(String id, Set<String> values) {
		ids.put(id,values);
	}

	public void addId(String id, String... values) {
		Set<String> set = new HashSet<String>();
		for (String value : values)
			set.add(value);
		ids.put(id,set);
	}
/*
	public void add(String providerName, String dt, String field, String value) {
		Map<String,Entry> provider = providers.get(providerName);
		if (provider==null) 
			provider = new HashMap<String, Entry>();

		Entry entry = provider.get(dt);
		if (entry ==null) 
			entry = new Entry();

		entry.set(providerName,dt,field,value);
		provider.put(entry.type,entry);
		providers.put(providerName, provider);
	}
*/

	private Date created = new Date();
	public Date getCreated() { return created; }
	public void setCreated(Date created) { this.created = created; } 
 
	private Date updated = new Date();
	public Date getUpdated() { return updated; }
	public void setUpdated(Date updated) { this.updated = updated; } 

	public Map<String,Set<String>> ids = new HashMap<String,Set<String>>();
	public Map<String,Set<String>> getIds() { return ids; }
	public void setIds(Map<String,Set<String>> ids) { this.ids = ids; } 

	@Override
	public String toString() {
		return "id="+id
			+" created="+created.getTime()
			+" updated="+updated.getTime()
			//+" cd="+cd
			//+" ad="+ad
			//+" providers.size="+providers.size()
			+" ids.size="+ids.size()
			;
	}
}
