package com.bootstrapium.config;

import static com.bootstrapium.config.SystemProperty.SAUCE_KEY;
import static com.bootstrapium.config.SystemProperty.SAUCE_USER;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.saucerest.SauceREST;

public class Sauce {

    private static final SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(
            SAUCE_USER.getValue(), SAUCE_KEY.getValue());

    private static final SauceREST client = new SauceREST(authentication.getUsername(),
            authentication.getAccessKey());
    
    public static URL getURL() throws MalformedURLException {
        return new URL(String.format(
                "http://%s:%s@ondemand.saucelabs.com:80/wd/hub",
                authentication.getUsername(), authentication.getAccessKey()));
    }

    public static boolean isDesired() {
        return SAUCE_USER.isSpecified() && SAUCE_KEY.isSpecified();
    }

    public static void updateJobName(
            SauceOnDemandSessionIdProvider sessionIdProvider, String name) {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("name", name);
        client.updateJobInfo(sessionIdProvider.getSessionId(), updates); 
    }
    
    public static void uploadFile(File file) throws IOException {
    	client.uploadFile(file);
    }
}