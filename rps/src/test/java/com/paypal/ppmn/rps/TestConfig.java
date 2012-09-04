package com.paypal.ppmn.rps;

import java.util.*;
import java.io.*;

public class TestConfig {
	private String baseUrl; 
	public String getBaseUrl() { return baseUrl; }
	public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; } 
 

	@Override
	public String toString() {
		return
			  "baseUrl=" + baseUrl 
		;
	}
}
