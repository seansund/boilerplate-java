package com.dex.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@ApplicationPath("rest")
@Path("sample")
public class SimpleService extends Application {
	public static final String PATH = "sample";

	private static final Map<String, Thinger> DATA = new LinkedHashMap<String, Thinger>();
	static {
		put(new Thinger().withId("thing1").withName("Thing One"));
		put(new Thinger("thing2", "Thing Two"));
	}
	
	private static void put(Thinger t) {
		DATA.put(t.getId(), t);
	}
	
	@GET
	@Produces("application/json")
	public List<Thinger> listThingers() {
		List<Thinger> result = new ArrayList<Thinger>(DATA.values());
		
		return result;
	}
	
	@GET
	@Path("{id}")
	@Produces("application/json")
	public Thinger getThinger(@PathParam("id") String id) {
		Thinger t = DATA.get(id);
		
		if (t == null) {
			throw new WebApplicationException("Thinger with id '" + id + "' cannot be found.", 404);
		}
		
		return t;
	}
	
	@PUT
	@Consumes("application/json")
	public Response createThinger(Thinger in) {
		Response r = null;
		
		if (in != null && in.getName() != null) {
			// 'name' has to be unique
			if (isUnique(in)) {
				String id = getUniqueId();
				in.setId(id);
				DATA.put(id, in);
				
				try {
					URI uri = new URI(PATH + "/" + id);
					r = Response.created(uri).build();
				} catch (URISyntaxException e) {
					r = Response.serverError().entity("Error creating Thinger").build();
				}
			} else {
				r = Response.status(Status.CONFLICT).entity("Thinger with name '" + in.getName() + "' already exists").build();
			}
		} else {
			r = Response.status(Status.PRECONDITION_FAILED).entity("Invalid request").build();
		}
		
		return r;
	}
	
	private boolean isUnique(Thinger in) {
		List<Thinger> data = new ArrayList<Thinger>(DATA.values());
		
		Comparator<Thinger> c = new Thinger.NameComparator();
		Collections.sort(data, c);
		int index = Collections.binarySearch(data, in, c);
		
		return index < 0;
	}
	
	private String getUniqueId() {
		List<Thinger> tmp = new ArrayList<Thinger>(DATA.values());
		return "thing" + tmp.size()+1;
	}
}