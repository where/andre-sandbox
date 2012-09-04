package com.paypal.ppmn.rps.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Profile {
	public String id;
	public String cd;
	public String ad;
	public Map<String,Map<String,Entry>> providers = new HashMap<String,Map<String,Entry>>();

	public Profile() {
	}

	public Profile(String id) {
		this.id = id;
		//this.cd = ""+System.currentTimeMillis();
		this.cd = ""+new Date();
		this.ad = this.cd;
	}

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

	@Override
	public String toString() {
		return "id="+id
			+" cd="+cd
			+" ad="+ad
			+" providers.size="+providers.size()
			;
	}
}
