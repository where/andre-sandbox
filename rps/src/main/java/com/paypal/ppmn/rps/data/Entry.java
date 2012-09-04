package com.paypal.ppmn.rps.data;


public class Entry {
	public String provider;
	public String type;
	public String field;
	public String value;

	public Entry(String provider, String type, String field, String value) {
		this();
		set(provider,type,field,value);
	}

	public Entry(){}

	public void set(String provider, String type, String field, String value){
		this.provider = provider;
		this.type = type;
		this.field = field;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "provider="+provider
			+" type="+type
			+" field="+field
			+" value="+value
			;
	}
}
