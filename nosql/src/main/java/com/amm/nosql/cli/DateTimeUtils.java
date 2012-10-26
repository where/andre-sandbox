package com.amm.nosql.cli;

import java.util.*;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.DateTime;

public class DateTimeUtils {
	private static	DateTimeFormatter parser = ISODateTimeFormat.dateTimeNoMillis();

	public static Date toDate(String isoFormat) {
		DateTime dtime = parser.parseDateTime(isoFormat);
		Date date = new Date(dtime.getMillis());
		return date;
	}
}
