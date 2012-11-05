package com.amm.vtest.plugins.datagen;

import java.util.*;
import java.io.*;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * Displays generated keys.
 */
public class KeyGeneratorDriver {
	private final Options options = new Options();
	private final String TAB="\t";
	private JCommander jcommander;
	private KeyGenerator keyGenerator ;

	public static void main(String [] args) {
		(new KeyGeneratorDriver()).process(args);
		System.exit(0);
	}

	void process(String [] args) {
		init(args);
		info("Max="+options.max+" Bean="+options.bean+" Config="+options.configFile);

		if (options.showIndex) {
			for (int j=0 ; j < options.max ; j++) {
				String key = keyGenerator.getNextKey();
				//info(TAB+j+". "+key);
				info(j+". "+key);
			}
		} else {
			for (int j=0 ; j < options.max ; j++) {
				String key = keyGenerator.getNextKey();
				info(key);
			}
		}
	}

	private void init(String [] args) {
		jcommander = new JCommander(options, args);
		initSpring();
	}

	private void initSpring() {
		info("configFile: "+options.configFile);
		ApplicationContext context = new ClassPathXmlApplicationContext(options.configFile);
		keyGenerator = (KeyGenerator)context.getBean(options.bean);
		info("keyGenerator: "+keyGenerator+"");
		info("keyGenerator.class="+keyGenerator.getClass().getName());
	}

	class Options {
		@Parameter(names = { "-b", "--bean" }, description = "Bean", required = true )
		public String bean = "help";

		@Parameter(names = { "-c", "--configFile" }, description = "Spring Config File", required = true )
		public String configFile = "appContext-datagen.xml";

		@Parameter(names = { "-m", "--max" }, description = "max")
		public int max = 10;

		@Parameter(names = { "-i", "--index" }, description = "Show index")
		public boolean showIndex = false ;

		@Parameter(names = { "-s", "--summary" }, description = "Show only summary")
		public boolean showOnlySummary = false ;
	}

	void usage() {
		jcommander.usage();
	}

	private void info(Object o) { System.out.println(o);}
	private void error(Object o) { System.out.println("ERROR: "+o);}
}
