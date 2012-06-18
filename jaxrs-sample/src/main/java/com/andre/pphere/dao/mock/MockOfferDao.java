package com.andre.pphere.dao.mock;

import java.util.*;
import org.apache.log4j.Logger;
import com.andre.pphere.dao.OfferDao;
import com.andre.pphere.data.*;

/**
 * Mock Offer DAO.
 */
public class MockOfferDao implements OfferDao {
	private static final Logger logger = Logger.getLogger(MockOfferDao.class);
	private Map<Integer,Offer> offers = new LinkedHashMap<Integer,Offer>();
	private int currentOfferId = 0 ;

	public MockOfferDao(List<Offer> loffers) {
		for (Offer offer: loffers) {
			offer.setId(currentOfferId);
			offers.put(currentOfferId++, offer);
		}
	}

	private Integer getOfferId(String name) {
		for (Map.Entry<Integer,Offer> entry : offers.entrySet()) {
			Offer offer = entry.getValue();
			if (name.equals(offer.getProductId())) {
				return entry.getKey();
			}
		}
		return null;
	}


	public MockOfferDao(int numOffers) {
		createOffers(numOffers) ;
	}

	private void createOffers(int numOffers) {
		for (int j=0 ; j < numOffers ; j++) {
			Offer offer = new Offer();
			offer.setId(j);
			offer.setProductId(j);
			offer.setPaypalId(j);
			offers.put(j,offer);
		}
		currentOfferId = numOffers ;
		logger.debug("numOffers="+numOffers+" currentOfferId="+currentOfferId);
	}

	public List<Offer> getOffers(Double lat, Double lng, Double radius) {
		List<Offer> list = new ArrayList<Offer>(offers.values());
		return list;
	}

	public Offer getOffer(int id) {
		return offers.get(id);
	}

	 public int createOffer(Offer offer) {
		logger.debug("currentOfferId="+currentOfferId);
		offer.setId(currentOfferId);
		offers.put(currentOfferId, offer);
		int oldId = currentOfferId ;
		currentOfferId++;
		logger.debug("currentOfferId="+currentOfferId+" oldId="+oldId);
		return oldId;
	}

	 public void updateOffer(Offer offer) {
		logger.debug("updateOffer: offer="+offer);
		offers.put(offer.getId(), offer);
	}

	 public void deleteOffer(int id) {
		offers.remove(id);
	}
}
