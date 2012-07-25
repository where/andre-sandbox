package com.andre.mapper;

import java.util.*;


public class Utils {
	public static final Date REF_DATE = new Date(1325783420000L);

	static Video createVideo() {
		Video obj =  new Video();
		obj.setId(2000L);
		obj.setName("Touching The Void");
		obj.setCode(2012);
		obj.setRatio(0.123);
		obj.addKeyword("Peru");
		obj.addKeyword("Siula Grande");
		obj.addAttribute("day","monday");
		obj.addAttribute("height","22");
		//obj.setStartDate(refDate);
		return obj;
	}
}
