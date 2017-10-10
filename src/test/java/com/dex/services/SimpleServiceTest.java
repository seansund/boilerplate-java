package com.dex.services;

import java.io.InputStream;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleServiceTest extends JerseyTest {

	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(SimpleService.class);
    }
	
	@Test 
	public void testListGoodResponse() {
		Response r = target("sample").request().get();
		
		Assert.assertEquals("Should return 200 status", 200, r.getStatus());
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<?> l = objectMapper.readValue((InputStream)r.getEntity(), List.class);
			Assert.assertEquals(true, !l.isEmpty());
		} catch (Exception e) {
			Assert.fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetGoodResponse() {
		Response r = target("sample/thing1").request().get();
		
		Assert.assertEquals("Should return 200 status", 200, r.getStatus());
	}
	
	@Test
	public void testGetBadResponse() {
		Response r = target("sample/thing3").request().get();
		
		Assert.assertEquals("Should return 404 status", 404, r.getStatus());
	}
	
	@Test
	public void testGoodCreate() {
		Entity<Thinger> e = Entity.json(new Thinger("Thing Three"));
		Response r = target("sample").request().put(e);
		
		Assert.assertEquals("Should return 'created' response", Response.Status.CREATED.getStatusCode(), r.getStatus());
		Assert.assertEquals("'Location' header should be returned", true, r.getHeaderString("Location") != null);
	}
	
	//@Test
	public void testBadCreate() {
		Entity<Thinger> e = Entity.json(new Thinger("Thing One"));
		Response r = target("sample").request().put(e);
		
		Assert.assertEquals("Should return 'confict' response", Response.Status.CONFLICT.getStatusCode(), r.getStatus());
	}
	
}