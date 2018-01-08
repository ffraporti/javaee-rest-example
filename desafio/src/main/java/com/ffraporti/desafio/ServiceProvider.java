package com.ffraporti.desafio;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
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
	
	/**
	 * getAll()
	 * 
	 * Gets the Provider`s ArrayList and builds into a JSON object
	 * 
	 * @return All providers saved on the system.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll()
	{
		return Response.status(Response.Status.OK).entity(providers).build();
	}
	
	/***
	 * getById(int id)
	 * 
	 * Looks for a specific provider based on its ID
	 * 
	 * @param id - Integer to be used as key for the provider's list
	 * @return Not Found or the Provider as JSON object
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getById(@PathParam("id") int id)
	{
		Provider provider = findById(id);
		
		if(provider == null) {
			return Response.status(Response.Status.NOT_FOUND).entity(ResponseMessages.NOT_FOUND.toString()).build();
		} else {
			return Response.status(Response.Status.OK).entity(provider).build();
		}		
	}
	
	/**
	 * create(Provider data)
	 * 
	 * Adds a provider to the system.
	 * 
	 * @param data - JSON object representing the provider
	 * @return
	 * 	-> Bad Request: if the data received on the message's body is not valid as a Provider
	 *  -> Conflict: if the system already contains a provider with the same Id as the received
	 *  -> Created: if the data is successfully added to the system
	 */
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
		
		return Response.status(Response.Status.CREATED).entity(ResponseMessages.SUCCESSFULLY_ADDED.toString()).build();
	}
	
	/**
	 * update(int id)
	 * 
	 * Updates data added to the system
	 * 
	 * @param id - Integer to be used as key for the provider's list
	 * @return
	 *  -> Bad Request: if the data received on the message's body is not valid as a Provider
	 *  -> Not Modified: if the system does not contain any provider with the received id
	 *  -> Accepted: if the data is successfully updated on the system
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Provider data) {
		
		if(data == null || !data.isValid()) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ResponseMessages.NO_BODY_PROVIDED.toString()).build();
		}
	
		Provider internalProvider = findById(data.getId());
		
		if(internalProvider == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity(ResponseMessages.RESOURCE_NOT_PRESENT.toString()).build();
		}
		
		providers.remove(internalProvider);
		providers.add(data);
		
		return Response.status(Response.Status.ACCEPTED).entity(ResponseMessages.SUCCESSFULLY_UPDATED.toString()).build();
	}
	
	/**
	 * remove(int id)
	 * 
	 * Removes a provider from the system
	 * 
	 * @param id - Integer to be used as key for the provider's list
	 * @return
	 *  -> Not Modified: if the system does not contain any provider with the received id
	 *  -> Ok: if the system successfully remove the provider
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response remove(@PathParam("id") int id) {
			
		Provider internalProvider = findById(id);
		
		if(internalProvider == null) {
			return Response.status(Response.Status.NOT_MODIFIED).entity(ResponseMessages.RESOURCE_NOT_PRESENT.toString()).build();
		}
		
		providers.remove(internalProvider);
		
		return Response.status(Response.Status.OK).entity(ResponseMessages.SUCCESSFULLY_REMOVED.toString()).build();
	}
	
	/**
	 * findById(int id)
	 * 
	 * Finds a Provider by its Id
	 * 
	 * @param id - Integer to be used as key for the provider's list
	 * @return
	 *  -> the provider object, if it's found
	 *  -> null if it is not found
	 */
	private Provider findById(int id) {
		
		for(Provider p : providers) {			
			if(p.getId() == id) {
				return p;
			}			
		}
		
		return null;		
	}

}