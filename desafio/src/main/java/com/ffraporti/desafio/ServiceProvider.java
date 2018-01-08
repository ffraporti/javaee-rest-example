package com.ffraporti.desafio;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("provider")
public class ServiceProvider {
	
	static private List<Provider> providers = new ArrayList<Provider>();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll()
	{
		return Response.status(Response.Status.OK).entity(providers).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getById(@PathParam("id") int id)
	{
		Provider provider = findById(id);
		
		if(provider == null) {
			return Response.status(Response.Status.OK).entity(ResponseMessages.NOT_FOUND.toString()).build();
		} else {
			return Response.status(Response.Status.OK).entity(provider).build();
		}		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Provider data) {
		
		if(data == null || !data.isValid()) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ResponseMessages.NO_BODY_PROVIDED.toString()).build();
		}
	
		if(findById(data.getId()) != null) {
			return Response.status(Response.Status.CONFLICT).entity(ResponseMessages.RESOURCE_ALREADY_PRESENT.toString()).build();
		}
		
		providers.add(data);
		
		return Response.status(Response.Status.CREATED).entity(ResponseMessages.SUCCESSFULLY_ADDED).build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id) {
		
		return Response.status(Response.Status.CREATED).entity(ResponseMessages.SUCCESSFULLY_ADDED).build();
	}
	
	private Provider findById(int id) {
		
		for(Provider p : providers) {			
			if(p.getId() == id) {
				return p;
			}			
		}
		
		return null;		
	}

}