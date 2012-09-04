package com.paypal.ppmn.rps.service;

import com.paypal.ppmn.rps.data.Profile;

public interface ProfileService {

	public Profile get(String id) throws Exception ;

	public Profile save(Profile profile) throws Exception ;

	public void delete(String id) throws Exception ;
}
