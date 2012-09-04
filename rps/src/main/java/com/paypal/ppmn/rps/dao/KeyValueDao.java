package com.paypal.ppmn.rps.dao;

import java.util.*;

public interface KeyValueDao {
 	public Object get(String id) throws Exception ;
	public void put(String id, Object obj) throws Exception ;
	public void delete(String id) throws Exception;
}
