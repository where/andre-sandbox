package com.paypal.ppmn.rps.client;

import java.util.*;
import org.apache.log4j.Logger;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.paypal.ppmn.rps.client.ProfileClient;
import com.paypal.ppmn.rps.data.FormatUtils;
import com.paypal.ppmn.rps.data.Profile;
import com.paypal.ppmn.rps.data.Entry;

/**
 * Command-line program to exercise the RPS client.
 * @author amesarovic
 */
public class ClientDriver {
    private static Logger logger = Logger.getLogger(ClientDriver.class);
	private ProfileClient client;
	private Options options = new Options();
	private JCommander jcommander;

	String [] configFiles = { "appContext.xml" };

	void process(String [] args) throws Exception {
		init(args);

		try {
			process();
		} catch (Exception e)  {
			error("OUCH: "+e+" e.class="+e.getClass().getName());
			e.printStackTrace();
		}
		System.exit(0);
	}

	public void process() throws Exception {
		long t1 = System.currentTimeMillis();

		if ("get".equals(options.method)) {
			Profile profile = client.get(options.key);
			String sts = profile==null ? "NOT FOUND" : "FOUND";
			info("get: "+sts+" key="+options.key+" profile="+profile);
			FormatUtils.format(profile);
		} else if ("put".equals(options.method)) {
			if (options.value == null) {
				error("Missing value");
			} else {
				Profile profile = newProfile(options.key);

				info("put: key="+options.key+" profile="+profile);
				FormatUtils.format(profile);
				client.save(profile);
			} 

		} else if ("delete".equals(options.method)) {
			client.delete(options.key);

		} else if ("help".equals(options.method)) {
			usage();

		} else {
			error("Unknown method="+options.method);
		}
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
		logger.debug("configFiles="+Arrays.toString(configFiles));
		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);

		String clientBeanName = "localClient";
		String pvalue = System.getProperty("clientProvider");
		if (pvalue != null)
			clientBeanName = pvalue;
		logger.debug("clientBeanName="+clientBeanName);

		client = context.getBean(clientBeanName,ProfileClient.class);
		logger.debug("client="+client);
		logger.debug("client.class="+client.getClass().getName());
	}

	public static void main(String [] args) throws Exception {
		(new ClientDriver()).process(args);
	}

	class Options {
		@Parameter(names = { "-m", "--method" }, description = "Method", required = true )
		public String method = "help";

		@Parameter(names = { "-k", "--key" }, description = "Key", required = true )
		public String key ;

		@Parameter(names = { "-v", "--value" }, description = "Value")
		public String value ;

		@Parameter(names = { "-h", "--help" }, description = "Help")
		public boolean help = false;
	}

	void usage() {
		jcommander.usage();
	}

	Profile newProfile(String key) {
		Profile profile = new Profile(key);
		String provider = "MyProvider";
		String dt = "myDt";
		String field = "myField";
		String value = options.value;
		profile.add(provider, dt+"0", field, value+"0");
		profile.add(provider, dt+"1", field+"1", value+"1");

		provider = "YourProvider";
		profile.add(provider, dt, field, value);
		profile.add(provider, dt+"2", field+"2", value+"2");

		return profile;
	}

	void info(Object o) { System.out.println(o);}
	void debug(Object o) { System.out.println(">> "+o);}
	void error(Object o) { System.out.println("ERROR: "+o);}
}
