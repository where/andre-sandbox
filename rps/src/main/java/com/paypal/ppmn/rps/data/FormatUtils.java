package com.paypal.ppmn.rps.data;

import java.util.*;
import com.paypal.ppmn.rps.data.Profile;
import com.paypal.ppmn.rps.data.Entry;

/**
 */
public class FormatUtils {

	public static void format(Profile profile) {
		format(profile,null) ;
	}

	public static void format(Profile profile, String msg) {
		msg = msg==null ? "" : msg;

		if (profile == null) {
			info("Profile "+msg+": null");
		} else {
			info("Profile "+msg+":");
			info("  id="+profile.id);
			info("  cd="+profile.cd);
			info("  ad="+profile.ad);
			Map<String,Map<String,Entry>> providers = profile.providers ;
			info("  providers="+providers.size());

			for (Map.Entry<String,Map<String,Entry>> entry : providers.entrySet()) {
				Map<String,Entry> value = entry.getValue();
				//info("	entry: KEY="+entry.getKey()+" VALUE=["+value+"]");
				info("	KEY="+entry.getKey());
				info("	VALUE:");
				for (Map.Entry<String,Entry> entry2 : value.entrySet()) {
					info("	  KEY="+entry2.getKey()+" VALUE=["+entry2.getValue()+"]");
				}
			}
		}
	}
	static void info(Object o) { System.out.println(o);}
}
