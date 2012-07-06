package com.andre.mapper;

import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "video")
@XmlType(name = "Video")

public class Video implements java.io.Serializable {
	private String name; 
	public String getName() { return name; }
	public void setName(String name) { this.name = name; } 

	private long id; 
	public long getId() { return id; }
	public void setId(long id) { this.id = id; } 
 
	private int code; 
	public int getCode() { return code; }
	public void setCode(int code) { this.code = code; } 
 
	private double ratio; 
	public double getRatio() { return ratio; }
	public void setRatio(double ratio) { this.ratio = ratio; } 
 
	private List<String> keywords = new ArrayList<String>();
	public List<String> getKeywords() { return keywords; }
	public void setKeywords(List<String> keywords) { this.keywords = keywords; } 
	public void addKeyword(String keyword) { keywords.add(keyword); }

	private Map<String,String> attributes = new HashMap<String,String>();
	public Map<String,String> getAttributes() { return attributes; }
	public void setAttributes(Map<String,String> attributes) { this.attributes = attributes; } 
	public void addAttribute(String key, String value) { attributes.put(key,value); }

	private List<Genre> genres = new ArrayList<Genre>();
	public List<Genre> getGenres() { return genres; }
	public void setGenres(List<Genre> genres) { this.genres = genres; } 
	public void addGenre(Genre genre) { genres.add(genre) ; }
 
	private Genre mainGenre; 
	public Genre getMainGenre() { return mainGenre; }
	public void setMainGenre(Genre mainGenre) { this.mainGenre = mainGenre; } 

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true ;
		if ( !(obj instanceof Video) ) return false;
		Video bean = (Video) obj;

		if (name==null && bean.name==null) {
		} else if (name.length() != name.length()) {
			return false ;
		} else {
			if (!name.equals(bean.name)) return false ;
		}

		if (id != bean.id) return false ; 
		if (code != bean.code) return false ; 
		if (ratio != bean.ratio) return false ; 
		if (keywords.size() != bean.keywords.size()) return false ; 
		if (attributes.size() != bean.attributes.size()) return false ; 
		//if (genres != bean.genres) return false ; 
		return true;
	}
}
