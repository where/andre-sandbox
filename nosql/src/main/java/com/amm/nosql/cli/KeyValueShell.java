package com.amm.nosql.cli;

import java.util.*;
import java.io.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.amm.nosql.data.KeyValue;
import com.amm.nosql.data.FormatUtils;
import com.amm.nosql.dao.KeyValueDao;
import com.google.common.io.Files;

/**
 * Ye good ol' shell...
 */
public class KeyValueShell {
	private static final Logger logger = Logger.getLogger(KeyValueShell.class);
	private Options options = new Options();
	private JCommander jcommander;
    private static final String ROOT_CONFIG_FILE = "appContext.xml" ;
    private static final String PROPERTY_PROVIDER_CONFIG_FILE = "providerConfigFile";
    private KeyValueDao keyValueDao;
    private Map<String,KeyValueDao> keyValueDaos;

	public static void main(String [] args) throws IOException {
		(new KeyValueShell()).process(args);
		System.exit(0);
	}

	void process(String [] args) throws IOException {
		init(args);
		loop();
	}
	
	public void loop() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		printHeader();
		status();
		String stime = "";
		//String previousCommand = null ;
		String [] previousTokens = null ;

		for (;;) {
			print("\n"+stime+">> ") ;
			String buf = reader.readLine();
			info(buf);
			long timeStart = System.currentTimeMillis();
			if (buf==null) return; 
			if (buf.length() == 0) continue ;
	
			char ch = '?';
			String [] tokens = buf.split(" ");
			if (tokens.length == 0) continue;
			String cmd = tokens[0];
			ch = cmd.charAt(0) ;

			if (cmd.length()==1 && (ch == 'x' || ch == 'q')) // Exit application
				break ;

			if (cmd.startsWith("exit") || buf.startsWith("quit"))
				break ;

			if (CliUtils.isRepeatCommand(cmd)) {
				tokens = previousTokens;
				cmd = tokens[0];
			}

			try {
				processCommand(cmd, tokens) ;
			} catch (Exception e) {
				//error(""+e.getMessage());
				error(e);
				e.printStackTrace();
			}
			stime = ""+(System.currentTimeMillis()-timeStart);
			previousTokens = tokens;
		}
	}

	void processCommand(String cmd, String [] tokens) throws Exception {
		char ch = cmd.charAt(0);
		if (ch == '?' || ch=='h') {
			printHeader();
		} else if (cmd.equals("get") || cmd.equals("g") ) {
			get(tokens);
		} else if (cmd.equals("delete") || cmd.equals("d") ) {
			delete(tokens);
		} else if (cmd.equals("put") || cmd.equals("p") ) {
			put(tokens);
		} else if (cmd.equals("putf") || cmd.equals("p") ) {
			putFile(tokens);
		} else if (cmd.equals("status")) {
			status();
		} else {
			error("Unknown command: "+cmd);
			//FormatUtils.dump(cmd.getBytes());
		}
	}

	void put(String [] tokens) throws Exception { 
        checkArgs(tokens,2);
        String key = tokens[1];
        String value = tokens[2];

		KeyValue keyValue = new KeyValue(key);
		keyValue.setValue(value.getBytes());
		keyValueDao.put(keyValue);
	}

    void putFile(String [] tokens) throws Exception {
        checkArgs(tokens,2);
        String key = tokens[1];
        String filename = tokens[2];
        File file = new File(filename);
        if (!file.exists()) {
            error("File does not exist: "+file);
        } else {
            byte[] value = Files.toByteArray(file);
			KeyValue keyValue = new KeyValue(key);
			keyValue.setValue(value);
			keyValueDao.put(keyValue);
        }
    }

	void get(String [] tokens) throws Exception { 
		String key = getKey(tokens);
		KeyValue keyValue = keyValueDao.get(key);
		String sts = keyValue==null ? "NOT FOUND" : "FOUND";
		FormatUtils.print(keyValue,options.maxToDisplay); 
	}

	void delete(String [] tokens) throws Exception { 
		String key = getKey(tokens);
		keyValueDao.delete(key);
	}

	void status() {
		info("Status:");
        info("  keyValueDao.class: "+keyValueDao.getClass().getName());
        info("  keyValueDao:       "+keyValueDao);
        info("  maxToDisplay:      "+options.maxToDisplay);
	}
	
	String getKey(String [] tokens) {
		checkArgs(tokens,1);
		String key = tokens[1];
		return key;
	}
  
	private void printHeader() {
		//String sdate = TimeUtils.formatDateWithTime();
		String sdate = "" + new Date();
		info("");
		info("Time: "+sdate);
        info("+----- Key Value Shell -------------------------------------+");
        info("|  ?               - Help                                   |");
        info("|  get KEY         - get KEY                                |");
        info("|  delete KEY      - delete KEY                             |");
        info("|  put KEY VALUE   - put KEY VALUE                          |");
        info("|  putf KEY FILE   - put KEY FILE contents                  |");
        info("|  status          - status                                 |");
        info("|  q               - Quit shell                             |");
        info("+-----------------------------------------------------------+");
	}

	void checkArgs(String [] tokens, int n) {
		if (tokens.length < n+1) {
			throw new RuntimeException("Expecting "+n+" arguments");
		}
	}

	void init(String [] args) {
	 	jcommander = new JCommander(options, args);
		initSpring() ;
	}

	private String [] configFiles = { "appContext.xml" } ;

    private void initSpring() {
/* AMM 15 apr 2013
        String providerConfigFile = System.getProperty(PROPERTY_PROVIDER_CONFIG_FILE);
        if (providerConfigFile == null)
            throw new RuntimeException("Missing application context for system property: "+PROPERTY_PROVIDER_CONFIG_FILE);
        String [] configFiles = new String[2];
        configFiles[0] = ROOT_CONFIG_FILE;
        configFiles[1] = providerConfigFile ;
        logger.debug("configFiles="+Arrays.toString(configFiles));
*/

        ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);

        keyValueDao = context.getBean("keyValueDao",KeyValueDao.class);
        logger.debug("keyValueDao="+keyValueDao);
        logger.debug("keyValueDao.class="+keyValueDao.getClass().getName());

/* AMM 9 mar 2013
        //keyValueDaos = context.getBean("keyValueDao",Map<String,KeyValueDao>.class);
        keyValueDaos = (Map<String,KeyValueDao>) context.getBean("keyValueDao");
        logger.debug("keyValueDaos="+keyValueDaos);
        logger.debug("keyValueDaos.class="+keyValueDaos.getClass().getName());
*/
	}

	class Options {
		@Parameter(names = { "-f", "--file" }, description = "Put file" )
		public String file = "curl/sample.json" ;

		@Parameter(names = { "-d", "--maxToDisplay" }, description = "maximum characters of value to display" )
		public int maxToDisplay = 100 ;
	}

	void print(Object o) { System.out.print(o);}
	void info(Object o) { System.out.println(o);}
	void error(Object o) { System.out.println("ERROR: "+ o);}
}
