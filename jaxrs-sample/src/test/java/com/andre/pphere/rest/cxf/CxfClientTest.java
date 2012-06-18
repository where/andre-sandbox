package com.andre.pphere.rest.cxf;

import com.andre.jaxb.InvalidFormatException;
import com.andre.jaxb.IllegalSyntaxException;
import com.andre.pphere.rest.BaseTest;
import com.andre.pphere.data.Store;
import com.andre.pphere.data.*;
import java.util.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.testng.annotations.*;
import org.testng.Assert;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;

/**
 * Tests using CXF client proxies. Not yet fully working - fails for JSON response.
 * See: http://cxf.apache.org/docs/jax-rs-client-api.html
 */
@Test (groups = { "fails" })
public class CxfClientTest extends BaseTest {
	private static final Logger logger = Logger.getLogger(CxfClientTest.class);
	private ServiceProxy proxy ;

	@BeforeClass void beforeClass() throws Exception {
        proxy = JAXRSClientFactory.create(baseUrl, ServiceProxy.class);
		logger.debug("proxy="+proxy);
	}

	@Test
	public void testStores() throws Exception {
        StoreList stores = proxy.getStores();
		logger.debug("stores="+stores);
	}
}
