package com.paypal.ppmn.rps.client;

import com.paypal.ppmn.rps.service.ProfileService;
import com.paypal.ppmn.rps.data.Profile;

/**
 * @author amesarovic
 */
public interface ProfileClient extends ProfileService { 

	public Profile get(String id) throws Exception ; 
	public Profile save(Profile profile) throws Exception ;
	public void delete(String id) throws Exception ;

	public void ping() throws Exception ;
}
