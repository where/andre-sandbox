package com.andre.jetty;

import java.util.*;

/**
 * port=8080
 * host=localhost
 * webApp=build/dist/pphere.war
 * contextPath=/pphere
 */
public class Main {

	public static void main(String [] args) throws Exception {
		(new Main()).process(args) ;
	}

	void process(String [] args) throws Exception {
		if (args.length < 4) {
			error("Expecting Host Port WebAppPath ContextPath");
			return ;
		}
		process(args[0], Integer.parseInt(args[1]), args[2], args[3]);
	}

	void process(String host, int port, String webApp, String contextPath) throws Exception {
		JettyRunner runner = new JettyRunner();
		runner.addConnector(host,port) ;
		runner.bindContext(webApp , contextPath);
		runner.start();
	}

	void error(Object o) { System.out.println("ERROR: "+o);}
}
