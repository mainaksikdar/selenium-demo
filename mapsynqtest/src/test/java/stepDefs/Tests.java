package stepDefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.response.Response;

import pages.LandingPage;
import utils.ReusableMethods;
import utils.TestBase;

public class Tests extends TestBase {

	public WebDriver driver;
	LandingPage landingPage;
	Response resp;
	
	@Given("^user sends a request to mapsynq$")
	public void user_sends_a_request_to_mapsynq() throws Throwable {
		resp = apiReusableMethods.getResponse(testConfig.getUrl());
		driver = reusableMethods.getDriver(testConfig);
		driver.get(testConfig.getUrl());
	}

	@Then("^validates the status code of the response$")
	public void validates_the_status_code_of_the_response() throws Throwable {
	    Assert.assertTrue("Response didn't return proper status code", (200==resp.getStatusCode()));
	}

	@Given("^validates that map is being displayed$")
	public void validates_that_map_is_being_displayed() throws Throwable {
	    
	}

	@Given("^validates that the headers on the left tab are appearing properly$")
	public void validates_that_the_headers_on_the_left_tab_are_appearing_properly() throws Throwable {
	    
	}


}
