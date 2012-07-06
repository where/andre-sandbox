package com.andre.mapper;

import java.util.*;

public class Utils {
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
		return obj;
	}
}
