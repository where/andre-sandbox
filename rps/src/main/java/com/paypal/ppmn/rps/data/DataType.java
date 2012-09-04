package com.paypal.ppmn.rps.data;

public enum DataType {
	privacy("pr"),
	segments("sg"),
	retargeting("rk"),
	demographics("dm"),
	location("lo"),
	postal("pc");

	String name;
	private DataType(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
}
