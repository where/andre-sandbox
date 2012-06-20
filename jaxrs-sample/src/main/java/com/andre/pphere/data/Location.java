package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "location")
@XmlType(name = "Location", propOrder = {
	"street1",
	"street2",
	"neighborhood",
	"city",
	"state",
	"country",
	"zip",
	"lat",
	"lng"
})
public class Location { 
	public Location() {
	}

	private String street1; 
	@XmlElement(required=false)
	public String getStreet1() { return street1; }
	public void setStreet1(String street1) { this.street1 = street1; } 
 
	private String street2; 
	@XmlElement(required=false)
	public String getStreet2() { return street2; }
	public void setStreet2(String street2) { this.street2 = street2; } 
 
	private String neighborhood; 
	@XmlElement(required=false)
	public String getNeighborhood() { return neighborhood; }
	public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; } 
 
	private String city; 
	@XmlElement(required=false)
	public String getCity() { return city; }
	public void setCity(String city) { this.city = city; } 
 
	private String state; 
	@XmlElement(required=false)
	public String getState() { return state; }
	public void setState(String state) { this.state = state; } 
 
	private String country; 
	@XmlElement(required=false)
	public String getCountry() { return country; }
	public void setCountry(String country) { this.country = country; } 
 
	private String zip; 
	@XmlElement(required=false)
	public String getZip() { return zip; }
	public void setZip(String zip) { this.zip = zip; } 
 
	private Double lat; 
	@XmlElement(required=false)
	public Double getLat() { return lat; }
	public void setLat(Double lat) { this.lat = lat; } 
 
	private Double lng; 
	@XmlElement(required=false)
	public Double getLng() { return lng; }
	public void setLng(Double lng) { this.lng = lng; } 

	@Override
	public String toString() { 
		return
			"street1="+street1
			;
	}
}
