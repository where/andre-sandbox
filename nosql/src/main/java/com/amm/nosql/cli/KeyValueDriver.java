package com.amm.nosql.cli;

import java.util.*;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.amm.nosql.data.FormatUtils;
import com.amm.nosql.data.KeyValue;
import com.amm.nosql.dao.KeyValueDao;

/**
 * Command-line program to exercise the KeyValue DAO.
 * @author amesarovic
 */
public class KeyValueDriver {
	private KeyValueDao keyValueDao;
	private Options options = new Options();
	private JCommander jcommander;

	String CONFIG_ROOT = "applicationContext.xml" ;
	String PROP_CONFIG = "providerConfigFile";

	void process(String [] args) throws Exception {
		init(args);
		info("currentMillis="+System.currentTimeMillis());
		info("method="+options.method+" key="+options.key+" attrs=["+options.value+"]+ iterations="+options.iterations);

		try {
			processKeyValueDao();
		} catch (Exception e)  {
			error("OUCH: "+e+" e.class="+e.getClass().getName());
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void processKeyValueDao() throws Exception {
		long t1 = System.currentTimeMillis();
		boolean showIdx = options.iterations > 1;

		for (int j=0 ; j < options.iterations ; j++) {
			long _t1 = System.currentTimeMillis();
			if (showIdx) info("-- index "+j+" ----------");

			if ("get".equals(options.method)) {
				KeyValue keyValue = keyValueDao.get(options.key);
				String sts = keyValue==null ? "NOT FOUND" : "FOUND";
				//info("get"+(showIdx?("."+j):"")+": "+sts+" key="+options.key+" keyValue="+keyValue);
				info("get: "+sts+" key="+options.key+" keyValue="+keyValue);
				format(keyValue);

			} else if ("put".equals(options.method)) {
				KeyValue keyValue = new KeyValue(options.key);
				if (options.value != null) {
					keyValue.setValue(options.value.getBytes());
				}
				info("put: key="+options.key+" keyValue="+keyValue);
				keyValueDao.put(keyValue);

			} else if ("delete".equals(options.method)) {
				keyValueDao.delete(options.key);
	
			} else if ("map".equals(options.method)) {
				map();
	
			} else if ("help".equals(options.method)) {
				usage();
				break;
	
			} else {
				error("Unknown method="+options.method);
				break;
			}
			long _time = System.currentTimeMillis()-_t1;
			if (showIdx) info("Execution time="+_time);
		}

		long time = System.currentTimeMillis()-t1;
		double mean = time / options.iterations;
		info("Execution time="+time+" mean="+mean);
	}

	public void map() throws Exception {
		KeyValue keyValue ;
	}

	public boolean init(String [] args) throws Exception {
		jcommander = new JCommander(options, args);
		if (options.help) {
			usage();
			return false;
		}

		initSpring();
		return true;
	}

	private	void initSpring() throws Exception {
		String cfgFile = System.getProperty(PROP_CONFIG);
		if (cfgFile == null) throw new Exception("Missing application context for system property: "+PROP_CONFIG);	
		String [] configFiles = new String[2];
		configFiles[0] = CONFIG_ROOT;
		configFiles[1] = cfgFile ;
		debug("initSpring: configFiles="+Arrays.toString(configFiles));

		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		keyValueDao = context.getBean("keyValueDao",KeyValueDao.class);
		debug("initSpring: keyValueDao="+keyValueDao);
		debug("initSpring: keyValueDao.class="+keyValueDao.getClass().getName());
	}

	public static void main(String [] args) throws Exception {
		(new KeyValueDriver()).process(args);
	}

	class Options {
		@Parameter(names = { "-m", "--method" }, description = "Method", required = true )
		public String method = "help";

		@Parameter(names = { "-k", "--key" }, description = "Key", required = true )
		public String key ;

		@Parameter(names = { "-v", "--value" }, description = "Value")
		public String value ;

		@Parameter(names = { "-i", "--iterations" }, description = "Iterations")
		public int iterations = 1;

		@Parameter(names = { "-h", "--help" }, description = "Help")
		public boolean help = false;
	}

	void usage() {
		jcommander.usage();
	}

	void format(KeyValue keyValue) {
		if (keyValue == null) {
			info("KeyValue: null");
		} else {
			info("KeyValue:");
			info("  key="+keyValue.getKey());
			info("  value="+new String(keyValue.getValue())); // TODO: not always string!
		}
	}

	void info(Object o) { System.out.println(o);}
	void debug(Object o) { System.out.println(">> "+o);}
	void error(Object o) { System.out.println("ERROR: "+o);}
}
