package com.andre.jaxrs.data.json;

import java.util.*;

public class PagedList<T> {
	private List<Link> links = new ArrayList<Link>();
    private List<T> list ;

	public PagedList() {
    	list = new ArrayList<T>();
	}

	public PagedList(List<T> list) {
		this.list = list ;
	}

	protected List<T> getList() {
	  return list;
	}

    public int getCount() { 
		return list.size(); 
	}

    public void setCount(int count) { 
		// NOOP for JAXB complaint
	}

    private Integer startIndex ;
    public Integer getStartIndex() {
        return startIndex;
    }
    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    private Integer totalResults ;
    public Integer getTotalResults() {
        return totalResults;
    }
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public void add(T obj) {
        list.add(obj);
    }

	public List<Link> getLinks() {
	  return links;
	}

	public void addLink(Link link) {
		links.add(link);
	}
	public void setPrevious(String url) { links.add(new Link(url,"previous")); }
	public void setNext(String url) { links.add(new Link(url,"next")); }
	//public void addLink(Link link) { links.add(link) ; }

    @Override public String toString() {
        return
            "startIndex="+ startIndex
            +" totalResults="+ totalResults
            +" #list="+list.size()
            ;
    }
}
