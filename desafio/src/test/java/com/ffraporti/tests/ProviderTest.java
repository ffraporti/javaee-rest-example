package com.ffraporti.tests;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

public class ProviderTest {
	
	private String BASE_STRING = "http://localhost:8080/desafio/v1/provider";
	
	private int TEST_PROVIDER_ID = 1;
	private String TEST_PROVIDER = "{\"email\":\"ffraporti@gmail.com\", \"cnpj\":\"12345678901\", \"name\":\"mdm\", \"id\": " + TEST_PROVIDER_ID + ", \"comment\": \"Testing Comment\"}";
	private String TEST_PROVIDER_UPDATED = "{\"email\":\"ffraporti@hotmail.com\", \"cnpj\":\"XXXX5678901\", \"name\":\"Felipe\", \"id\": " + TEST_PROVIDER_ID + ", \"comment\": \"Testing Comment\"}";
	private String TEST_PROVIDER_UPDATED_ID = "{\"email\":\"ffraporti@hotmail.com\", \"cnpj\":\"XXXX5678901\", \"name\":\"Felipe\", \"id\": " + TEST_PROVIDER_ID + "99, \"comment\": \"Testing Comment\"}";
	private String TEST_PROVIDER_INVALID_EMAIL = "{\"email\":\"ffraporti\", \"cnpj\":\"12345678901\", \"name\":\"mdm\", \"id\": " + TEST_PROVIDER_ID + ", \"comment\": \"Testing Comment\"}";
	private String TEST_PROVIDER_SMALLER_CNPJ = "{\"email\":\"ffraporti@gmail.com\", \"cnpj\":\"1234567890\", \"name\":\"mdm\", \"id\": " + TEST_PROVIDER_ID + ", \"comment\": \"Testing Comment\"}";
	private String TEST_PROVIDER_BIGGER_CNPJ = "{\"email\":\"ffraporti@gmail.com\", \"cnpj\":\"123456789000\", \"name\":\"mdm\", \"id\": " + TEST_PROVIDER_ID + ", \"comment\": \"Testing Comment\"}";
	
	private String SUCCESS_ON_CREATION = "The resource was successfully added to the system.";
	private String SUCCESS_ON_DELETE = "The resource was successfully removed from the system.";
	private String SUCCESS_ON_UPDATE = "The resource was successfully updated.";
	private String CONFLICT_ON_CREATION = "The resource can't be added due to a conflict. This resource is already present.";
	private String WRONG_MSG_BODY = "Wrong message body. Check if the JSON content of the message fulfill all the requirements.";
	private String NOT_FOUND_ON_UPDATE = "The resource can't be updated, it was not found on the system.";
	private String OBJ_NOT_FOUND = "Object was not found";
	private String NOT_DELETED = null;
	
	/**
	 * This test checks the getAll route
	 * It should be empty
	 */
	@Test
	public void A_ProviderGetAll_Empty() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
		
		try {
			//Delete any possible user
			requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 0, "DELETE");
			
			//Request and check if is empty
			String str = requestWithoutBody(BASE_STRING, 200, "GET");			
			assertEquals("[]", str);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test checks the getById route
	 * As a first test, the return should be 404 Not Found with a message
	 */
	@Test
	public void B_ProviderGetById_NotFound() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
		
		try {
			//Delete any possible user
			requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 0, "DELETE");		
			
			String str = requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 404, "GET");
			assertEquals(OBJ_NOT_FOUND, str);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test checks an nonexistent route by passing a string as Id
	 * It should return 404 Not Found
	 */
	@Test
	public void C_InexistantRoute() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
		
		try {
			
			URL url = new URL(BASE_STRING + "/id");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json");	        
	        conn.getResponseMessage();
	        
	        assertEquals(404, conn.getResponseCode());
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the create method
	 * It should return 201 Created
	 */
	@Test
	public void D_PostCreate_Success() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;
		
		try {
			//Delete any possible user
			requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 0, "DELETE");
			
			String result = requestWithBody(201, TEST_PROVIDER, "POST");
	        assertEquals(SUCCESS_ON_CREATION, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the create method with a Repeated Id
	 * It should return 409 Conflict
	 */
	@Test
	public void E_PostCreate_Conflict() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
				
		try {
			//Insert a provider to ensure there will be a conflict
			requestWithBody(0, TEST_PROVIDER, "POST");
			
			String result = requestWithBody(409, TEST_PROVIDER, "POST");
			
	        assertEquals(CONFLICT_ON_CREATION, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the create method if the message body is null
	 * It should return 400 Bad Request
	 */
	@Test
	public void F_PostCreateBadRequest_NullBody() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
				
		try {
			//Delete any possible user
			requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 0, "DELETE");
			
			String result = requestWithBody(400, "{}", "POST");
			
	        assertEquals(WRONG_MSG_BODY, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the create method if the message body's email does not contain @
	 * It should return 400 Bad Request
	 */
	@Test
	public void G_PostCreateBadRequest_InvalidEmail() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
				
		try {
			//Delete any possible user
			requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 0, "DELETE");
			
			String result = requestWithBody(400, TEST_PROVIDER_INVALID_EMAIL, "POST");
			
	        assertEquals(WRONG_MSG_BODY, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the create method if the message body's cpnj length does not match the right length
	 * It should return 400 Bad Request
	 */
	@Test
	public void H_PostCreateBadRequest_InvalidCnpj() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
				
		try {
			//Delete any possible user
			requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 0, "DELETE");
			
			String result = requestWithBody(400, TEST_PROVIDER_SMALLER_CNPJ, "POST");
			
	        assertEquals(WRONG_MSG_BODY, result);
	        
			result = requestWithBody(400, TEST_PROVIDER_BIGGER_CNPJ, "POST");
			
	        assertEquals(WRONG_MSG_BODY, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test checks the getById route
	 * As a first test, the return should be 200 With the right object
	 */
	@Test
	public void I_ProviderGetById_Valid() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;
		
		try {
			//ensure a provider with Id = 1 will be there
			requestWithBody(0, TEST_PROVIDER, "POST");
			
			String str = requestWithoutBody(BASE_STRING + "/1", 200, "GET");
	        assertEquals("{\"comment\":\"Testing Comment\",\"email\":\"ffraporti@gmail.com\",\"cnpj\":\"12345678901\",\"name\":\"mdm\",\"id\":1}", str);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the update method
	 * It should return 202 ACCEPTED
	 */
	@Test
	public void J_PutUpdate_Success() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;
		
		try {
			//ensure a provider with Id = 1 will be there
			requestWithBody(0, TEST_PROVIDER, "POST");
			
			String result = requestWithBody(202, TEST_PROVIDER_UPDATED, "PUT");
	        assertEquals(SUCCESS_ON_UPDATE, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the update method with a user that doesn't exist
	 * It should return 304 Not Modified
	 */
	@Test
	public void K_PutUpdate_Nonexistent() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
				
		try {
			//Delete any possible user
			requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 0, "DELETE");
			
			String result = requestWithBody(304, TEST_PROVIDER_UPDATED_ID, "PUT");			
	        assertEquals(null, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the create method if the message body is null
	 * It should return 400 Bad Request
	 */
	@Test
	public void L_PutUpdateBadRequest_NullBody() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
				
		try {
			//ensure a provider with Id = 1 will be there
			requestWithBody(0, TEST_PROVIDER, "POST");
			
			String result = requestWithBody(400, "{}", "PUT");			
	        assertEquals("Wrong message body. Check if the JSON content of the message fulfill all the requirements.", result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the update method if the message body's email does not contain @
	 * It should return 400 Bad Request
	 */
	@Test
	public void M_PutUpdateBadRequest_InvalidEmail() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
				
		try {
			//ensure a provider with Id = 1 will be there
			requestWithBody(0, TEST_PROVIDER, "POST");
			
			String result = requestWithBody(400, TEST_PROVIDER_INVALID_EMAIL, "PUT");
			
	        assertEquals(WRONG_MSG_BODY, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test the update method if the message body's cpnj length does not match the right length
	 * It should return 400 Bad Request
	 */
	@Test
	public void N_PutUpdateBadRequest_InvalidCnpj() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
				
		try {
			//ensure a provider with Id = 1 will be there
			requestWithBody(0, TEST_PROVIDER, "POST");
			
			String result = requestWithBody(400, TEST_PROVIDER_SMALLER_CNPJ, "PUT");			
	        assertEquals(WRONG_MSG_BODY, result);	        
	        
			result = requestWithBody(400, TEST_PROVIDER_BIGGER_CNPJ, "POST");			
	        assertEquals(WRONG_MSG_BODY, result);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test tries to delete a nonexistent provider
	 * It should return 304 Not Modified
	 */
	@Test
	public void O_Delete_Nonexistent() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;	
		
		try {
			//Delete any possible user
			requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 0, "DELETE");
			
			//Delete nonexistent
			String str = requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 304, "DELETE");
	        assertEquals(NOT_DELETED, str);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
	
	/**
	 * This test tries to delete a provider
	 * It should return 200 Ok
	 */
	@Test
	public void P_Delete_Success() {
		Boolean EXPECTED = true;
		
		Boolean connectionResult = false;
		
		try {
			//ensure a provider with Id = 1 will be there
			requestWithBody(0, TEST_PROVIDER, "POST");
			
			String str = requestWithoutBody(BASE_STRING + "/" + TEST_PROVIDER_ID, 200, "DELETE");
	        assertEquals(SUCCESS_ON_DELETE, str);
	        
	        connectionResult = true;
		} catch(IOException e) {
			connectionResult = false;
		}
		
		assertEquals(EXPECTED, connectionResult);
	}
		
	/**
	 * Method used as helper to send request with body
	 * 
	 * @param errorCode the expected error code of the request (0 to ignore assertion)
	 * @param content the body content of the request
	 * @param method the method used for the request
	 * @return the server string response
	 * @throws IOException
	 */
	private String requestWithBody(int errorCode, String content, String method) throws IOException {
		BufferedReader br;
		
		URL url = new URL(BASE_STRING);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");	     
        conn.setDoOutput(true);
                
        String str = content;
        
        OutputStreamWriter outWriter = new OutputStreamWriter(conn.getOutputStream());
        outWriter.write(str);
        
        outWriter.flush();
        outWriter.close();
        
        conn.getResponseMessage();
        
        int responseCode = conn.getResponseCode();
        
        if(responseCode >= 200 && responseCode <= 399)
        	br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        else
        	br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        
        String s = br.readLine();
        
        if(errorCode != 0)
        	assertEquals(errorCode, responseCode);
        
        return s;
	}
	
	/**
	 * Method used as helper to send request with no body
	 * 
	 * @param baseUrl the url in which the server that will receive the request is localized
	 * @param errorCode the expected error code of the request (0 to ignore assertion)
	 * @param method the method used for the request
	 * @return the server string response
	 * @throws IOException
	 */
	private String requestWithoutBody(String baseUrl, int errorCode, String method) throws IOException{
		BufferedReader br;
			
		URL url = new URL(baseUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", "application/json");
        conn.getResponseMessage();
        
        int responseCode = conn.getResponseCode();
        
        if(responseCode >= 200 && responseCode <= 399 && conn.getInputStream() != null)
        	br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        else
        	br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        
        if(errorCode != 0)
        	assertEquals(errorCode,responseCode);
                
        return br.readLine(); 
	}

}
