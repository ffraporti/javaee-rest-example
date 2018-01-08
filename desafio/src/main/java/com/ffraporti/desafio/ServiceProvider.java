package com.ffraporti.desafio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
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
	
	private List<Provider> providers = new ArrayList<Provider>();
	
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
		for(Provider p : providers) {
			
			if(p.getId() == id) {
				return Response.status(Response.Status.OK).entity(p).build();
			}
			
		}
		
		return Response.status(Response.Status.OK).entity(ResponseMessages.NOT_FOUND).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create() {
		
		return Response.status(Response.Status.CREATED).entity("Created").build();
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id) {
		
		return Response.status(Response.Status.CREATED).entity("Created").build();
	}

}