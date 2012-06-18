package com.andre.pphere.dao;

import com.andre.pphere.data.*;
import java.util.*;

/**
 * Offer DAO.
 */
public interface OfferDao {
	public List<Offer> getOffers(Double lat, Double lng, Double radius) ;
	public Offer getOffer(int id) ;
	public int createOffer(Offer store);
	public void updateOffer(Offer store);
	public void deleteOffer(int id);
}
