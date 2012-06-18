package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import com.andre.rest.data.PagedList;

@XmlRootElement(name="offers")
public class OfferList extends PagedList {
	private List<Offer> offers;

	public OfferList() {
	}

	public OfferList(List<Offer> offers) {
	  this.offers = offers;
	}

	@XmlElement(name="offer")
	public List<Offer> getOffers() {
	  return offers;
	}
}
