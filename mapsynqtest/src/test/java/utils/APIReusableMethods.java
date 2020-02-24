package utils;

import java.util.HashMap;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class APIReusableMethods {

	public HashMap<String, Object> headersForms(){	
		HashMap<String, Object> headers = new HashMap<String,Object>();
		headers.put("Content-Type", "application/json");
		return headers;
	}
	
	public Response getResponse(String baseUri) {
		Response response = null;
		
		try {
		HashMap<String, Object> headers	= new HashMap<String,Object>();
		headers = headersForms();
		RestAssured.baseURI = baseUri;
		//log.info("Sending request to "+baseUri);
		response = RestAssured.given().
				when().log().all().headers(headers).get();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return response;
	}
}
