package com.paypal.ppmn.rps.data;

public enum Namespace {
	profile("profile"),
	postal("postal"),
	matchkey("matchkey"),
	ids("ids"),
	fc("fc");


	String name;
	private Namespace(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public String toString(){
		return name;
	}
}
