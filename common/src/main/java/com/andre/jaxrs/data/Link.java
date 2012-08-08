package com.andre.jaxrs.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Link")
@XmlType(name = "Link", propOrder = {
	"href",
	"rel"
})
public class Link implements java.io.Serializable {
	public Link() {} 

	public Link(String href) {
		this.href = href ;
	}
	public Link(String href, String rel) {
		this.href = href ;
		this.rel = rel ;
	}


	private String href=null; 
	@XmlAttribute(required=true)
	public String getHref() { return href; }
	public void setHref(String val) { href=val; } 

	private String rel=null; 
	@XmlAttribute(required=false)
	public String getRel() { return rel; }
	public void setRel(String val) { rel=val; } 

	@Override
	public String toString() { 
		return
			  " href="+href
			+ " rel="+rel
			;
	}
}
