package com.ffraporti.desafio;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("provider")
public class ServiceProvider {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll()
	{
		String output = "<h1>Hello World!<h1>" +
				"<p>RESTful Service is running ... <br>Ping @ " + new Date().toString() + "</p<br>";
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response getById(@PathParam("id") int id)
	{
		Provider provider = new Provider(id, "teste frap", "teste@gmail.com", "dlsaknsd", "ldsakm;");
		return Response.status(200).entity(provider).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{email}")
	public Response getByEmail(@PathParam("email") String email)
	{
		Provider provider = new Provider(2, "teste frap", email, "dlsaknsd", "ldsakm;");
		return Response.status(200).entity(provider).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{name}")
	public Response getByName(@PathParam("name") String name)
	{
		Provider provider = new Provider(3, name, "teste@gmail.com", "dlsaknsd", "ldsakm;");
		return Response.status(200).entity(provider).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{cnpj}")
	public Response getByCnpj(@PathParam("cnpj") String cnpj)
	{
		Provider provider = new Provider(6, "teste frap", "teste@gmail.com", "dlsaknsd", cnpj);
		return Response.status(200).entity(provider).build();
	}

}