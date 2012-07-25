package com.andre.mapper;

import java.util.*;
import org.apache.log4j.Logger;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "video")
@XmlType(name = "Video")

public class Video implements java.io.Serializable {
	private static final Logger logger = Logger.getLogger(Video.class);
	private String name; 
	public String getName() { return name; }
	public void setName(String name) { this.name = name; } 

	private long id; 
	public long getId() { return id; }
	public void setId(long id) { this.id = id; } 
 
	private int code; 
	public int getCode() { return code; }
	public void setCode(int code) { this.code = code; } 

	private String description ; 
	public String getDescription() { return description ; }
	public void setDescription(String description ) { this.description  = description ; } 
 
	private double ratio; 
	public double getRatio() { return ratio; }
	public void setRatio(double ratio) { this.ratio = ratio; } 

	private Date startDate; 
	public Date getStartDate() { return startDate; }
	public void setStartDate(Date startDate) { this.startDate = startDate; } 

	private Date endDate; 
	public Date getEndDate() { return endDate; }
	public void setEndDate(Date endDate) { this.endDate = endDate; } 
 
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

		//logger.debug("top: -----------------");

		if (!equals(startDate,bean.startDate)) return false;
		if (!equals(endDate,bean.endDate)) return false;

		if (name==null && bean.name==null) {
			;
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

	private boolean equals(Date date1, Date date2) {
		//logger.debug("top: obj1.time="+date1+" obj2.time="+date2);
		if (date1==null && date2==null) {
			//logger.debug("aa.1");
			;
		} else if (date1 == null || date2 == null) {
			//logger.debug("aa.2");
			return false;
		} else {
			//logger.debug("else: obj1.time="+date1.getTime()+" obj2.time="+date2.getTime());
			if (date1.getTime() != date2.getTime()) 
				return false;
		}
		return true;
	}
}
