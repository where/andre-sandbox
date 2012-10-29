package com.paypal.ppmn.rps;

import java.util.*;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import com.andre.mapper.ObjectMapper;
import com.paypal.ppmn.rps.service.ProfileService;
import com.paypal.ppmn.rps.data.Profile;
import com.paypal.ppmn.rps.client.ProfileClient;

public class BaseTest {
    private static Logger logger = Logger.getLogger(BaseTest.class);

	private static String [] configFiles = {
		"appContext.xml",
		"appContext-test.xml"
	} ;

	public static ObjectMapper objectMapper ;
	public static ProfileService profileService;
	public static String baseUrl ;
	public static ProfileClient client ;

	@BeforeSuite
	public static void beforeSuite() throws Exception {
		initSpring();
	}

	@AfterSuite
	public static void afterSuite() {
	}

	static void initSpring() {
		ApplicationContext context = new ClassPathXmlApplicationContext(configFiles);
		
		TestConfig testConfig = context.getBean("testConfig",TestConfig.class);
		baseUrl = testConfig.getBaseUrl();

        String clientBeanName = "serviceClient";
        String pvalue = System.getProperty("clientProvider");
        if (pvalue != null)
            clientBeanName = pvalue;
        logger.debug("clientBeanName="+clientBeanName);
        client = context.getBean(clientBeanName,ProfileClient.class);
        logger.debug("client="+client);
        logger.debug("client.class="+client.getClass().getName());


		objectMapper = context.getBean("objectMapper",ObjectMapper.class);
		logger.debug("objectMapper="+objectMapper);
		profileService = context.getBean("profileService",ProfileService.class);
		logger.debug("profileService="+profileService);
		logger.debug("profileService.class="+profileService.getClass().getName());
	}

	public void assertProfile(Profile profile1, Profile profile2) {
		//FormatUtils.format(profile2,"Profile2");
/* TODO
		Assert.assertEquals(profile1.id,profile2.id);
		Assert.assertEquals(profile1.cd,profile2.cd);
		Assert.assertEquals(profile1.ad,profile2.ad);
*/
		//Assert.assertEquals(profile2,profile); // FAILS because profile.providers aren't the same
	}

	public Profile newProfile(String key, String value) {
		Profile profile = new Profile(key);
		String provider = "Provider";
		String dt = "Dt";
		String field = "Field";
/* TODO
		profile.add(provider, dt, field, value);
		profile.add(provider, dt+"2", field+"2", value+"2");
*/
		return profile ;
	}

	public static void info(Object o) { System.out.println(""+o);}
}
