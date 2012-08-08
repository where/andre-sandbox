package com.andre.jaxrs.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

/** Under construction */
@XmlTransient
public class OldPagedList implements java.io.Serializable  {
	private List<Link> links = new ArrayList<Link>();
    //private List<?> list = new ArrayList<?>();

	public OldPagedList() {
	}

    private int count = 0 ;
    @XmlElement(required=true)
    public int getCount() { return count; }
    public void setCount(int count) { this.count=count; }

	@XmlElement(name="link")
	public List<Link> getLinks() {
	  return links;
	}

	public void addLink(Link link) {
		links.add(link);
	}
	public void setPrevious(String url) { links.add(new Link(url,"previous")); }
	public void setNext(String url) { links.add(new Link(url,"next")); }
	//public void addLink(Link link) { links.add(link) ; }
}
