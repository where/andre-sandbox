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
	}

/*
	public Profile get(String id) throws Exception {
		logger.debug("id="+id);
		Map<String,Object> fields = keyValueDao.get(id);
		logger.debug("fields.size="+fields.size());
	}
*/

	public Profile get(String id) throws Exception {
		logger.debug("id="+id);
		Map<String,Object> fields = (Map<String,Object>)keyValueDao.get(id);
		logger.debug("fields="+fields);

		if (fields == null) {
			return null;
		}
		logger.debug("fields.size="+fields.size());
		
		Profile profile =  new Profile();
		profile.id = id;
		profile.cd = (String)fields.get("cd");
		profile.ad = (String)fields.get("ad");
		return profile;
	}

	public Profile save(Profile profile) throws Exception {
		logger.debug("profile="+profile);

		Profile profile2 = get(profile.id);
		logger.debug("profile2="+profile2);
		if (profile2 != null) {
			profile2.ad = profile.ad;
			profile2.cd = profile.cd;
		} else {
			profile2 = profile;
		}

		Map<String,Object> fields = new HashMap<String, Object>();
		fields.put("ad",profile2.ad);
		fields.put("cd",profile2.cd);

		logger.debug("#fields="+fields.size());
		keyValueDao.put(profile2.id, fields);

		return profile2;
	}

	public void delete(String id) throws Exception {
		keyValueDao.delete(id);
	}

}
