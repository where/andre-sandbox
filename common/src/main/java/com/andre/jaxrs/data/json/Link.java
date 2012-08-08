package com.andre.jaxrs.data.json;

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
	public String getHref() { return href; }
	public void setHref(String val) { href=val; } 

	private String rel=null; 
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
