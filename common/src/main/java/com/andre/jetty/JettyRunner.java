package com.andre.jetty;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.apache.log4j.Logger;

/**
 * Jetty 6 embedded HTTP server.
 */
public class JettyRunner {
	private static Server server = new Server();
	private static final Logger logger = Logger.getLogger(JettyRunner.class);

	public JettyRunner() {
		server.setStopAtShutdown(true);
	}

	public void addConnector(String host, int port) {
		logger.debug("host="+host+" , port="+port);
		Connector connector = new SelectChannelConnector();
		connector.setPort(port);
		connector.setHost(host);
		server.addConnector(connector);
	}

	public JettyRunner bindContext(String webApp , String contextPath){
		logger.debug("contextPath="+contextPath);
		logger.debug("webApp="+webApp);
		WebAppContext context = new WebAppContext(webApp, contextPath);
		context.setParentLoaderPriority(true);
		server.setHandler(context);
		return this;
	}

	public void start() throws Exception {
		server.start();
	}
}
