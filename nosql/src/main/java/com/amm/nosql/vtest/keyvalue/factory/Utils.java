package com.amm.nosql.vtest.keyvalue.factory;

import java.util.*;
import com.rits.cloning.Cloner;

public class Utils {

/*
	@SuppressWarnings("unchecked")
	public static  T cloneMe(T t) {
		return null;
	}
*/

	public static Object clone(Object obj) {
		Cloner cloner=new Cloner();
		Object cloned = cloner.deepClone(obj);
		return cloned;
	}

}
