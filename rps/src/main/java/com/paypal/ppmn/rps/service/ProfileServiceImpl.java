package com.paypal.ppmn.rps.service;

import java.util.*;
import org.apache.log4j.Logger;
import com.paypal.ppmn.rps.data.Profile;
import com.paypal.ppmn.rps.dao.KeyValueDao;

public class ProfileServiceImpl implements ProfileService {
	private static Logger logger = Logger.getLogger(ProfileServiceImpl.class);
	private KeyValueDao keyValueDao;

	public ProfileServiceImpl(KeyValueDao keyValueDao) {
		this.keyValueDao = keyValueDao;
		logger.debug("keyValueDao="+keyValueDao.getClass().getName());
	}


	public Profile get(String id) throws Exception {
		logger.debug("id="+id);
		Profile profile = (Profile)keyValueDao.get(id);
		logger.debug("profile="+profile);
		return profile;
	}

	public Profile save(Profile profile) throws Exception {
		logger.debug("profile="+profile);
		keyValueDao.put(profile.id, profile);
		return profile;
	}

	public void delete(String id) throws Exception {
		keyValueDao.delete(id);
	}

}
