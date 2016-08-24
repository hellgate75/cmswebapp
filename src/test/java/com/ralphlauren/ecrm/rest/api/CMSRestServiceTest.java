package com.ralphlauren.ecrm.rest.api;

import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.ralphlauren.ecms.stores.rest.api.CMSRestService;

public class CMSRestServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(CMSRestService.class);
    }

    /**
     * Test to see that the message "Got it!" is sent in the response.
     */
    @Test
    public void testGetIt() {
        final String responseMsg = target().path("cms/version").request().get(String.class);

        assertEquals("{\"serviceCode\":\"cms-stores\",\"versionId\":\"1.0.0\",\"services\":[\"version\"]}", responseMsg);
    }

}
