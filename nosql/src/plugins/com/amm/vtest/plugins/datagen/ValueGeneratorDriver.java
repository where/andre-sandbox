package com.amm.vtest.plugins.datagen;

import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

/**
 * Value generator CLI.
 */
public class ValueGeneratorDriver {
	private static final DecimalFormat fmt = new DecimalFormat();
	private final Options options = new Options();
	private JCommander jcommander;
	ValueGenerator valueGenerator ;
	long seed = 1 ; int baseSize = 10 ; int range =180;

	public static void main(String [] args) {
		(new ValueGeneratorDriver()).process(args);
		System.exit(0);
	}

	void process(String [] args) {
		info("Requests="+options.requests+" Bean="+options.bean+" Config="+options.configFile+" useSpring="+options.useSpring);
		process2(args);
	}

	void process1(String [] args) {
		init(args);
		info("Requests="+options.requests+" Bean="+options.bean+" Config="+options.configFile+" useSpring="+options.useSpring);
		info("showOnlySummary="+options.showOnlySummary);
		long total = 0 ;
		if (!options.showOnlySummary) 
			info("ValueLen	ValueContent");
		for (int j=0 ; j < options.requests ; j++) {
			String value = valueGenerator.getValue();
				int len = value.length();
			if (!options.showOnlySummary) {
				info("	"+(options.showIndex?j+". ":"")+String.format(" %4d",len)
					+ (options.showValue?"	 "+value:""));
			}
			total += len;
		}
		double mean = (double)total / (double)options.requests;
		info("Requests="+options.requests+" Bytes: total="+total+" mean="+mean);
		info("Requests="+fmt.format(options.requests)+" Bytes: total="+fmt.format(total)+" mean="+mean);
	}

	void process2(String [] args) {
		long seed1 = 1776 ;
		long seed2 = 1812 ;
		RandomValueSizeGenerator valueGenerator1 = new RandomValueSizeGenerator(seed1, baseSize, range);
		RandomValueSizeGenerator valueGenerator2 = new RandomValueSizeGenerator(seed2, baseSize, range);
		int matches = 0 ;
		for (int j=0 ; j < options.requests ; j++) {
			String value1 = valueGenerator1.getValue();
			String value2 = valueGenerator2.getValue();
			//info("	"+j+". Lengths="+value1.length()+" "+value2.length());
			if (value1.equals(value2)) {
				info("Warning: values equals for idx="+j+" value.length="+value1.length()+" value="+value1);
				matches++;
			}
		}
		info("#matches="+matches);
	}

	private void init(String [] args) {
		jcommander = new JCommander(options, args);
		//configFiles = options.configFiles.toArray(new String[0]);
		if (options.useSpring)
			initSpring();
		else
			initNonSpring();
		info("valueGenerator: "+valueGenerator+"");
		info("valueGenerator.class="+valueGenerator.getClass().getName());
	}

	private void initNonSpring() {
		info("seed="+seed+" baseSize="+baseSize+" range="+range);
		valueGenerator = new RandomValueSizeGenerator(seed, baseSize, range);
	}

	private void initSpring() {
		ApplicationContext context = new ClassPathXmlApplicationContext(options.configFile);
		valueGenerator = (ValueGenerator)context.getBean(options.bean);
	}

	class Options {
		@Parameter(names = { "-b", "--bean" }, description = "Bean", required = true )
		public String bean = "help";

		@Parameter(names = { "-c", "--configFile" }, description = "Spring Config File", required = true )
		public String configFile = "help";

		@Parameter(names = { "-r", "--requests" }, description = "requests")
		public int requests = 20;

		@Parameter(names = { "-i", "--index" }, description = "Show index")
		public boolean showIndex = false ;

		@Parameter(names = { "-v", "--value" }, description = "Show value")
		public boolean showValue = false ;

		@Parameter(names = { "-s", "--spring" }, description = "Use Spring")
		public boolean useSpring = false ;

		@Parameter(names = { "-S", "--summary" }, description = "Show only summary")
		public boolean showOnlySummary = false ;
	}

	void usage() {
		jcommander.usage();
	}

	void info(Object o) { System.out.println(o);}
	void error(Object o) { System.out.println("ERROR: "+o);}
}
