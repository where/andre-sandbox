package com.amm.nosql.cli;

import java.util.*;
import java.io.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class CliUtils {
	private static final Logger logger = Logger.getLogger(CliUtils.class);

	public static boolean isRepeatCommand(String cmd) {
		//logger.debug(" cmd="+cmd);
		//logger.debug(" cmd.length="+cmd.length());
		if (cmd.length() != 3) return false;
		byte [] content = cmd.getBytes();
		return (content[0] == 27 && content[1] == 91 && content[2] == 65) ;
	}

}
