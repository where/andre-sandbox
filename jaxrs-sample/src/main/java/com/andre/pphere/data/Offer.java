package com.andre.pphere.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "offer")
@XmlType(name = "Offer", propOrder = {
	"productId",
	"paypalId",
	"numberTotal",
	"id"
})
public class Offer extends BaseObject {
	public Offer() {} 

	public Offer(Long id) {
		setId(id);
	}

	private Integer productId; 
	@XmlElement(required=true)
	public Integer getProductId() { return productId; }
	public void setProductId(Integer productId) { this.productId = productId; } 
 
	private Integer paypalId; 
	@XmlElement(required=true)
	public Integer getPaypalId() { return paypalId; }
	public void setPaypalId(Integer paypalId) { this.paypalId = paypalId; } 
 
	private Integer numberTotal; 
	@XmlElement(required=false)
	public Integer getNumberTotal() { return numberTotal; }
	public void setNumberTotal(Integer numberTotal) { this.numberTotal = numberTotal; } 
 
/*
	private Integer numberRedeemed; 
	@XmlElement(required=false)
	public Integer getNumberRedeemed() { return numberRedeemed; }
	public void setNumberRedeemed(Integer numberRedeemed) { this.numberRedeemed = numberRedeemed; } 
 
	private Integer numberClaimed; 
	@XmlElement(required=false)
	public Integer getNumberClaimed() { return numberClaimed; }
	public void setNumberClaimed(Integer numberClaimed) { this.numberClaimed = numberClaimed; } 
 
	private Integer numberRemaining; 
	@XmlElement(required=false)
	public Integer getNumberRemaining() { return numberRemaining; }
	public void setNumberRemaining(Integer numberRemaining) { this.numberRemaining = numberRemaining; } 
 
	private Integer percentRedeemed; 
	public Integer getPercentRedeemed() { return percentRedeemed; }
	public void setPercentRedeemed(Integer percentRedeemed) { this.percentRedeemed = percentRedeemed; } 
 
	private Integer percentClaimed; 
	public Integer getPercentClaimed() { return percentClaimed; }
	public void setPercentClaimed(Integer percentClaimed) { this.percentClaimed = percentClaimed; } 
 
	private Integer percentRemaining; 
	public Integer getPercentRemaining() { return percentRemaining; }
	public void setPercentRemaining(Integer percentRemaining) { this.percentRemaining = percentRemaining; } 
*/

	@Override
	public String toString() { 
		return
			"id="+getId()
			;
	}
}
