package com.paypal.ppmn.rps.client;

import org.apache.log4j.Logger;
import com.paypal.ppmn.rps.service.ProfileService;
import com.paypal.ppmn.rps.data.Profile;

/**
 * Service service implementation of Client interface.
 * @author amesarovic
 */
public class ServiceProfileClient implements ProfileClient {
    private static Logger logger = Logger.getLogger(ServiceProfileClient.class);
	private ProfileService profileService ;
	
	public ServiceProfileClient(ProfileService profileService) {
		this.profileService = profileService;
	}

	public Profile get(String id) throws Exception {
		return profileService.get(id);
	}

	public Profile save(Profile profile) throws Exception {
		return profileService.save(profile);
	}

	public void delete(String id) throws Exception {
		profileService.delete(id);
	}

    public void ping() throws Exception {
	}
}
