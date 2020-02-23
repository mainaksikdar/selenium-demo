package stepDefs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
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
			Reporter.addStepLog(e.getMessage());
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
	

	@Given("^validates that live is the current header which is active$")
	public void validates_that_live_is_the_current_header_which_is_active() {
		try {
			Assert.assertTrue("Live is not active", landingPage.validateLiveIsActive());
		}
		catch(Exception e) {
			
		}
	}

	@Given("^incidents is the current tab which is active$")
	public void incidents_is_the_current_tab_which_is_active() {
		try {
			Assert.assertTrue("Incidents is not active", landingPage.validateIncidentsIsActive());
		}
		catch(Exception e) {
			
		}
	}

	@Then("^system displays the list of incidents$")
	public void system_displays_the_list_of_incidents() {
		try {
			Assert.assertTrue("No incident is being displayed", landingPage.validateIncidentsAreDisplayed());
		}
		catch(Exception e) {
			
		}
	}

	@Then("^upon clicking each incident system displays the incident on the map$")
	public void upon_clicking_each_incident_system_displays_the_incident_on_the_map() {
		try {
			Assert.assertTrue("Incident(s) found not clickable", landingPage.clickEachIncident().size()==0);
		}
		catch(Exception e) {
			
		}
	}
	
	@Given("^clicks on camera tab$")
	public void clicks_on_camera_tab() {
	    try {
	    	landingPage.clickCamerasHeading();
	    }
	    catch(Exception e) {
	    	
	    }
	    
	}

	@Given("^verifies search box being displayed$")
	public void verifies_search_box_being_displayed() {
	    try {
	    	landingPage.verifyCameraSearchBox();
	    }
	    catch(Exception e) {
	    	
	    }
	}

	@Then("^searches for a location from among the list$")
	public void searches_for_a_location_from_among_the_list() {
	    try {
	    	landingPage.searchForCameraPlace();
	    }
	    catch(Exception e) {
	    	
	    }
	   
	}

	@Then("^system displays only the specific location that has been searched$")
	public void system_displays_only_the_specific_location_that_has_been_searched() {
	    try {
	    	Assert.assertTrue("The location in the search result does not match", landingPage.verifySearchedPlaceResult());
	    }
	    catch(Exception e) {
	    	
	    }
	}

	@Then("^user clicks on the search result$")
	public void user_clicks_on_the_search_result() {
	    try {
	    	landingPage.clickOnSearchResult();
	    }
	    catch(Exception e) {
	    	
	    }
	}

	@Then("^system displays the same on the map$")
	public void system_displays_the_same_on_the_map() {
	    try {
	    	Assert.assertTrue(landingPage.validateSearchedPlaceIsDisplayedOnMap());
	    }
	    catch(Exception e) {
	    	
	    }
	}
	
	@Given("^clicks on tolls tab$")
	public void clicks_on_tolls_tab() {
	    try {
	    	landingPage.clickOnTolls();
	    }
	    catch(Exception e) {
	    	landingPage.clickOnTolls();
	    }
	    
	}

	@Given("^verifies search box for tolls is being displayed$")
	public void verifies_search_box_for_tolls_is_being_displayed() {
	    try {
	    	landingPage.verifyTollSearchBox();
	    }
	    catch(Exception e) {
	    	
	    }
	}

	@Then("^searches for a location from among the toll list$")
	public void searches_for_a_location_from_among_the_toll_list() {
	    try {
	    	landingPage.searchForTollList();
	    }
	    catch(Exception e) {
	    	
	    }
	}

	@Then("^system displays only the specific place that has been searched$")
	public void system_displays_only_the_specific_place_that_has_been_searched() {
	    try {
	    	Assert.assertTrue("The location of toll in the search result does not match", landingPage.verifySearchedTollResult());
	    }
	    catch(Exception e) {
	    	
	    }
	}

	@Then("^user clicks on the search result of the tolls$")
	public void user_clicks_on_the_search_result_of_the_tolls() {
	    try {
	    	landingPage.clickOnTollSearchResult();
	    }
	    catch(Exception e) {
	    	
	    }
	}

	@Then("^system displays the specific toll details on the map$")
	public void system_displays_the_specific_toll_details_on_the_map() {
	    try {
	    	Assert.assertTrue(landingPage.validateSearchedTollIsDisplayedOnMap());
	    }
	    catch(Exception e) {
	    	
	    }
	}







	@After
	public void cleanUp()
	{
		if(driver!=null)
			driver.quit();
	}
	
	
}
