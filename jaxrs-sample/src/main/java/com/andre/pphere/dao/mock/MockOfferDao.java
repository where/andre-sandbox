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
	private Map<Long,Offer> offers = new LinkedHashMap<Long,Offer>();
	private long currentOfferId = 0 ;

	public MockOfferDao(List<Offer> loffers) {
		for (Offer offer: loffers) {
			offer.setId(currentOfferId);
			offers.put(currentOfferId++, offer);
		}
	}

	private Long getOfferId(String name) {
		for (Map.Entry<Long,Offer> entry : offers.entrySet()) {
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
			offer.setId((long)j);
			offer.setProductId(j);
			offer.setPaypalId(j);
			offers.put((long)j,offer);
		}
		currentOfferId = numOffers ;
		logger.debug("numOffers="+numOffers+" currentOfferId="+currentOfferId);
	}

	public List<Offer> getOffers(Double lat, Double lng, Double radius) {
		List<Offer> list = new ArrayList<Offer>(offers.values());
		return list;
	}

	public Offer getOffer(long id) {
		return offers.get(id);
	}

	 public long createOffer(Offer offer) {
		logger.debug("currentOfferId="+currentOfferId);
		offer.setId(currentOfferId);
		offers.put(currentOfferId, offer);
		long oldId = currentOfferId ;
		currentOfferId++;
		logger.debug("currentOfferId="+currentOfferId+" oldId="+oldId);
		return oldId;
	}

	 public void updateOffer(Offer offer) {
		logger.debug("updateOffer: offer="+offer);
		offers.put(offer.getId(), offer);
	}

	 public void deleteOffer(long id) {
		offers.remove(id);
	}
}
