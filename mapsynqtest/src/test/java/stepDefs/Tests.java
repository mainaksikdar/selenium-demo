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
	public void user_sends_a_request_to_mapsynq() throws Exception {
		
			resp = apiReusableMethods.getResponse(testConfig.getUrl());
			Reporter.addStepLog("Request has been triggered");
		
	}

	@Then("^validates the status code of the response$")
	public void validates_the_status_code_of_the_response() throws Exception {
		
			Assert.assertTrue("Response didn't return proper status code", (200==resp.getStatusCode()));
			Reporter.addStepLog("The request returned 200 status code in the response");
		
	}
	
	@Given("^user launches the site$")
	public void user_launches_the_site() throws Exception{
		
			driver = reusableMethods.getDriver(testConfig);
			reusableMethods.launchPage(testConfig.getUrl());
			landingPage = new LandingPage(driver);
			landingPage.logoDisplayed();
			Reporter.addStepLog("User is able to launch the site");
	}

	@Given("^validates that map is being displayed$")
	public void validates_that_map_is_being_displayed() throws Exception{
	    
	    	landingPage.validateMapIsDisplayed();
	    	Reporter.addStepLog("Map is being displayed correctly");
	}

	@Given("^validates that the headers on the left tab are appearing properly$")
	public void validates_that_the_headers_on_the_left_tab_are_appearing_properly() throws Exception {
	    landingPage.validateHeadersOnLeftTab();
	    Reporter.addStepLog("Headers on the left tab are appearing as expected");
	}
	

	@Given("^validates that live is the current header which is active$")
	public void validates_that_live_is_the_current_header_which_is_active() throws Exception {
		Assert.assertTrue("Live is not active", landingPage.validateLiveIsActive());
		Reporter.addStepLog("Live is currently active");
	}

	@Given("^incidents is the current tab which is active$")
	public void incidents_is_the_current_tab_which_is_active() throws Exception{
		Assert.assertTrue("Incidents is not active", landingPage.validateIncidentsIsActive());
		Reporter.addStepLog("Incidents is currently active");
	}

	@Then("^system displays the list of incidents$")
	public void system_displays_the_list_of_incidents() throws Exception{
		Assert.assertTrue("No incident is being displayed", landingPage.validateIncidentsAreDisplayed());
		Reporter.addStepLog("System is displaying the list of all incidents");
	}

	@Then("^upon clicking each incident system displays the incident on the map$")
	public void upon_clicking_each_incident_system_displays_the_incident_on_the_map() throws Exception {
			Assert.assertTrue("Incident(s) found not clickable", landingPage.clickEachIncident().size()==0);
			Reporter.addStepLog("Each incident is clickable and is being displayed on the map");
	}
	
	@Given("^clicks on camera tab$")
	public void clicks_on_camera_tab() throws Exception{
	    landingPage.clickCamerasHeading();
	    Reporter.addStepLog("User has clicked the camera tab");
	}

	@Given("^verifies search box being displayed$")
	public void verifies_search_box_being_displayed() throws Exception{
	    landingPage.verifyCameraSearchBox();
	    Reporter.addStepLog("Search box is being displayed");
	}

	@Then("^searches for a location from among the list$")
	public void searches_for_a_location_from_among_the_list() throws Exception{
	    landingPage.searchForCameraPlace();
	    Reporter.addStepLog("User is able to search for a location");
	   
	}

	@Then("^system displays only the specific location that has been searched$")
	public void system_displays_only_the_specific_location_that_has_been_searched() throws Exception {
	    Assert.assertTrue("The location in the search result does not match", landingPage.verifySearchedPlaceResult());
	    Reporter.addStepLog("System is displaying the specific location which has ben searched");
	}

	@Then("^user clicks on the search result$")
	public void user_clicks_on_the_search_result() throws Exception{
	   landingPage.clickOnSearchResult();
	   Reporter.addStepLog("User is able to click on the search result");
	}

	@Then("^system displays the same on the map$")
	public void system_displays_the_same_on_the_map() throws Exception{
	    Assert.assertTrue(landingPage.validateSearchedPlaceIsDisplayedOnMap());
	    Reporter.addStepLog("System is displaying the same camera location on the map");
	}
	
	@Given("^clicks on tolls tab$")
	public void clicks_on_tolls_tab() throws Exception{
	    landingPage.clickOnTolls();
	    Reporter.addStepLog("User is able to click the tolls tab");
	    
	}

	@Given("^verifies search box for tolls is being displayed$")
	public void verifies_search_box_for_tolls_is_being_displayed() throws Exception{
	    landingPage.verifyTollSearchBox();
	    Reporter.addStepLog("System is displaying the search box for tolls");
	}

	@Then("^searches for a location from among the toll list$")
	public void searches_for_a_location_from_among_the_toll_list() throws Exception{
	    landingPage.searchForTollList();
	    Reporter.addStepLog("User is able to search for a toll");
	}

	@Then("^system displays only the specific place that has been searched$")
	public void system_displays_only_the_specific_place_that_has_been_searched() throws Exception{
	    Assert.assertTrue("The location of toll in the search result does not match", landingPage.verifySearchedTollResult());
	    Reporter.addStepLog("System displays the toll which has been searched");
	}

	@Then("^user clicks on the search result of the tolls$")
	public void user_clicks_on_the_search_result_of_the_tolls() throws Exception{
	    	landingPage.clickOnTollSearchResult();
	    	Reporter.addStepLog("User is able to click on the search result of the tolls");
	}

	@Then("^system displays the specific toll details on the map$")
	public void system_displays_the_specific_toll_details_on_the_map() throws Exception{
	    	Assert.assertTrue(landingPage.validateSearchedTollIsDisplayedOnMap());
	    	Reporter.addStepLog("System displays the specific toll on the map");
	}
	
	@After
	public void cleanUp() throws Exception	{
		if(driver!=null)
			driver.quit();
	}
	
	
}
