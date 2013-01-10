package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.*;
import com.andre.rest.data.PagedList;

@XmlRootElement(name="offers")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "OfferList", propOrder = {
	"count",
	"links",
	"offers",
})  
public class OfferList extends PagedList<Offer> {
	
	public OfferList() {
	}
		
	public OfferList(List<Offer> offers) {
		super(offers);
	}
	
	@XmlElement(name="offer")
	public List<Offer> getOffers() { 
		return getList();
	}
} 

