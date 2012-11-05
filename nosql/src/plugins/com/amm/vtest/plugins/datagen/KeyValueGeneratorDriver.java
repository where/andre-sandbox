package com.amm.vtest.plugins.datagen;

import java.util.*;
import java.io.*;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.axm.web.dom.*;
import com.axm.web.dom.widget.TextProvider;
import com.axm.web.dom.widget.BaseReporterProvider;
import java.text.*;

/**
 * Displays generated keys.
 */
public class KeyValueGeneratorDriver {
	private final Options options = new Options();
	private final String TAB="\t";
	private JCommander jcommander;
	private KeyGenerator keyGenerator ;
	private ValueGenerator valueGenerator ;
    private BaseReporterProvider rprovider = new TextProvider();
	private Long lowerIndex = null;
	private Long upperIndex = null;

	public static void main(String [] args) {
		(new KeyValueGeneratorDriver()).process(args);
		System.exit(0);
	}

	void process(String [] args) {
		init(args);
		info("Max="+options.max+" BeanKeyGenerator="+options.beanKeyGenerator+" Config="+options.configFile);

		String sep=" ";
		long valueSize = 0;
		long keySize = 0;
		long ms1 = System.currentTimeMillis();
		long ns1 = System.nanoTime();
		for (int j=0 ; j < options.max ; j++) {
			if (options.filterIndex > j) continue ;
			String key = keyGenerator.getNextKey();
			String value = valueGenerator.getValue();
			if (!options.donotShowData) {
				String svalue = options.showOnlyValueSize ? " value.size="+value.length() : value;
				String prefix = options.showIndex ? ""+j+". " : "" ;
				info(prefix+key+sep+svalue);
			}
			valueSize += value.length();
			keySize += key.length();
		}
		long ns = System.nanoTime()-ns1;
		long ms = System.currentTimeMillis()-ms1;

		long totalSize = valueSize+keySize;
		double meanTotalSize = totalSize / options.max ;

		double meanKeySize = keySize / options.max ;
		double meanValueSize = valueSize / options.max ;
		
		double meanMs = ms / options.max ;
		double meanNs = ns / options.max ;
/*
		info("Total key size:   "+keySize+" mean="+meanKeySize);
		info("Total value size: "+valueSize+" mean="+meanValueSize);
		info("Total size:       "+totalSize+" mean="+meanTotalSize);

		info("Latency ms        "+ms+" mean="+meanMs);
		info("Latency ns        "+ns+" mean="+meanNs);
*/

		DomTable table = new DomTable(1,2,0);

		addRow(table,null,"Size","Mean");
		addRow(table,"Total key size", keySize, meanKeySize);
		addRow(table,"Total value size", valueSize, meanValueSize);
		addRow(table,"Total size", totalSize, meanTotalSize);
		addRow(table,"Latency ms", ms, meanMs);
		addRow(table,"Latency ns", ns, meanNs);

		//info("-----------");
		info("");
		info("Requests: "+withCommas(options.max));
		info("");
		info(rprovider.convert(table));
	}

	void addRow(DomTable table, String title, Object value, Object mean) {
		DomRow row = table.addRow();
		row.addCell(title).setAlign(DomCell.LEFT);
		String svalue = ""+value;
		if (value instanceof Long) 
			svalue = withCommas((Long)value);
		row.addCell(svalue).setAlign(DomCell.RIGHT);
		row.addCell(mean).setAlign(DomCell.RIGHT);
	}

	private void init(String [] args) {
		jcommander = new JCommander(options, args);
		initSpring();
/*
		if (options.keyRange != null) {
			String [] toks = options.keyRange.split(",");
			if (toks.length == 2) {
				lowerIndex = Integer.parseInt(toks[0]);
				upperIndex = Integer.parseInt(toks[1]);
			}
		}
*/
	}

	private void initSpring() {
		info("configFile: "+options.configFile);
		ApplicationContext context = new ClassPathXmlApplicationContext(options.configFile);
		keyGenerator = context.getBean(options.beanKeyGenerator,KeyGenerator.class);
		valueGenerator = context.getBean(options.beanValueGenerator,ValueGenerator.class);
		info("keyGenerator: "+keyGenerator);
		info("keyGenerator.class: "+keyGenerator.getClass().getName());
		info("valueGenerator: "+valueGenerator);
		info("valueGenerator.class: "+valueGenerator.getClass().getName());
	}

	class Options {

		@Parameter(names = { "--beanKeyGenerator" }, description = "Bean Key Generator", required = true )
		public String beanKeyGenerator ;

		@Parameter(names = { "--beanValueGenerator" }, description = "Bean Value Generator", required = true )
		public String beanValueGenerator ;

		@Parameter(names = { "-c", "--configFile" }, description = "Spring Config File", required = true )
		//public String configFile = "vtest.xml";
		public String configFile = "appContext-datagen.xml";

		@Parameter(names = { "-m", "--max" }, description = "max")
		public int max = 10;

		@Parameter(names = {"--showIndex" }, description = "Show index")
		public boolean showIndex = false ;

		@Parameter(names = {"--donotShowData" }, description = "Do not show data")
		public boolean donotShowData ;

		@Parameter(names = { "-s", "--showOnlyValueSize" }, description = "Show only value size")
		public boolean showOnlyValueSize = false ;

		@Parameter(names = { "--filterIndex" }, description = "Filter index" )
		public int filterIndex ;

		//@Parameter(names = { "--keyRange" }, description = "Key index range: 20,30")
		//public String keyRange ;
	}

	void usage() {
		jcommander.usage();
	}

	public static final DecimalFormat formatter = new DecimalFormat("#,###");

	public static String withCommas(long num) {
	return String.format("%s",formatter.format(num));
	}

	private void info(Object o) { System.out.println(o);}
	private void error(Object o) { System.out.println("ERROR: "+o);}
}
