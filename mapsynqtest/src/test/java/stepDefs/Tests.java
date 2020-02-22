package stepDefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import com.cucumber.listener.Reporter;

import cucumber.api.java.After;
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
	public void user_sends_a_request_to_mapsynq() {
		try {
			resp = apiReusableMethods.getResponse(testConfig.getUrl());
			Reporter.addStepLog("Request has been triggered");
		}
		catch(Exception e) {
			Reporter.addStepLog("Request could not be triggered");
		}
	}

	@Then("^validates the status code of the response$")
	public void validates_the_status_code_of_the_response() {
		try {
			Assert.assertTrue("Response didn't return proper status code", (200==resp.getStatusCode()));
		    //test.log(LogStatus.PASS, "The request returned 200 status code in the response");
		}
		catch(Exception e)	{
			//test.log(LogStatus.FAIL, "The request returned "+resp.getStatusCode());
		}
	}
	
	@Given("^user launches the site$")
	public void user_launches_the_site() {
		try {
			driver = reusableMethods.getDriver(testConfig);
			reusableMethods.launchPage(testConfig.getUrl());
			landingPage = new LandingPage(driver);
			//test.log(LogStatus.PASS, "The site has been launched");
		}
		catch(Exception e) {
			//test.log(LogStatus.FAIL, e.getMessage());
		}
	}

	@Given("^validates that map is being displayed$")
	public void validates_that_map_is_being_displayed() {
	    try {
	    	landingPage.validateMapIsDisplayed();
	    	//test.log(LogStatus.PASS, "The map is being displayed");
	    }
	    catch(Exception e) {
	    	//test.log(LogStatus.FAIL, e.getMessage());
	    }
	}

	@Given("^validates that the headers on the left tab are appearing properly$")
	public void validates_that_the_headers_on_the_left_tab_are_appearing_properly() {
	    try {
	    	landingPage.validateHeadersOnLeftTab();
	    	//test.log(LogStatus.PASS, "The headers on the left tab are being displayed");
	    }
	    catch(Exception e) {
	    	//test.log(LogStatus.FAIL, e.getMessage());
	    }
	}

	@After
	public void cleanUp()
	{
		if(driver!=null)
			driver.quit();
		//report.endTest(test);
	}
	
	
}
