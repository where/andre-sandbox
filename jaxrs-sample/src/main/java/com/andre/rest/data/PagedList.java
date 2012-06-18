package com.andre.rest.data;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

/** Under construction */
public class PagedList {
	private List<Link> links = new ArrayList<Link>();

	public PagedList() {
	}

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
