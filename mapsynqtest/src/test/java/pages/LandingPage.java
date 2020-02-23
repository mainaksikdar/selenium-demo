package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.cucumber.listener.Reporter;

import utils.ReusableMethods;

public class LandingPage {
	
	public WebDriver driver;
	ReusableMethods reusableMethods;
	String place;
	WebElement targetElement;
	
	@FindBy(how = How.XPATH, using = "//div[@id='OpenLayers.Map_5_OpenLayers_ViewPort']")
	WebElement mapSection;
	
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'directions_tab')]")
	WebElement directionsTab;
	
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'me_tab')]")
	WebElement personalTab;
	
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'live_tab')]")
	WebElement liveTab;
	
	@FindBy(how = How.XPATH, using = "//label[@for='rdIncidents']")
	WebElement incidentsHeading;
	
	@FindBy(how = How.XPATH, using = "//label[@for='rdCameras']")
	WebElement camerasHeading;
	
	@FindBy(how = How.XPATH, using = "//label[@for='rdErp']")
	WebElement tollsHeading;
	
	@FindBy(how = How.XPATH, using = "//ul[@id='search_incident_singapore']/li")
	List<WebElement> individualIncident;
	
	@FindBy(how = How.CLASS_NAME, using = "popuptitle")
	WebElement popupTitle;
	
	@FindBy(how = How.ID, using = "popup_contentDiv")
	WebElement popupDiv;
	
	@FindBy(how = How.ID, using = "txtSearchCamerasingapore")
	WebElement searchCameras;
	
	@FindBy(how = How.XPATH, using = "//ul[@id='camera_location_singapore']//div[@class='camera_list']/a")
	List<WebElement> cameraLocations;
	
	@FindBy(how = How.XPATH, using = "//body//div")
	WebElement cameraDesc;
	
	@FindBy(how = How.ID, using = "txtSearchERPsingapore")
	WebElement searchTolls;
	
	@FindBy(how = How.XPATH, using = "//ul[@id='erp_locationsingapore']//div[@class='erp_list']/a")
	List<WebElement> tollLocations;
	
	@FindBy(how = How.XPATH, using = "//body//label")
	WebElement tollName;
	
	@FindBy(how = How.XPATH, using = "//a[@class='header_logo sprite']")
	WebElement logo;
	
	public LandingPage(WebDriver driver) {
		try {
			this.driver=driver;
			reusableMethods = new ReusableMethods();
			PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
		}

		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
		}
	}
	
	public void logoDisplayed() throws Exception{
		try {
			reusableMethods.verifyIfPresent(driver, logo, "logo");
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public void validateMapIsDisplayed() throws IOException {
		try {
			reusableMethods.verifyIfPresent(driver, mapSection, "mapSection");
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public void validateHeadersOnLeftTab() throws IOException	{
		try	{
			reusableMethods.verifyIfPresent(driver, directionsTab, "directionsTab");
			reusableMethods.verifyIfPresent(driver, personalTab, "personalTab");
			reusableMethods.verifyIfPresent(driver, liveTab, "liveTab");
			reusableMethods.verifyIfPresent(driver, incidentsHeading, "incidentsHeading");
			reusableMethods.verifyIfPresent(driver, camerasHeading, "camerasHeading");
			reusableMethods.verifyIfPresent(driver, tollsHeading, "tollsHeading");
		}
		catch (Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public boolean validateLiveIsActive() throws IOException {
		boolean isActive = false;
		
		try {
			isActive = reusableMethods.validateIfActive(liveTab, "tab_active");
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		
		return isActive;
	}
	
	public boolean validateIncidentsIsActive() throws IOException {
		boolean isActive = false;
		try {
			isActive = reusableMethods.validateIfActive(incidentsHeading, "ui-state-active");
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return isActive;
	}
	
	public boolean validateIncidentsAreDisplayed() throws IOException {
		boolean incidentsShown=false;
		try {
			if(individualIncident.size()>=1)
				incidentsShown = true;
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return incidentsShown;
	}
	
	public List<String> clickEachIncident() throws Exception{
		List<String> unclicked = new ArrayList<String>();
		try {
			for(WebElement incident : individualIncident)
			{
				reusableMethods.clickElement(driver, incident);
				reusableMethods.explicitWait(driver, popupDiv);
				if(validateIncidentDisplayedOnMap(incident)==false)
					unclicked.add(incident.getText());
			}
			
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return unclicked;
	}
	
	public boolean validateIncidentDisplayedOnMap(WebElement element) throws IOException {
		boolean isDisplayed = false;
		try {
			reusableMethods.verifyIfPresent(driver, element, "popupDiv");
			String text1 = element.getText();
			String text2 = popupTitle.getText();
			if(!text2.equals("") && popupDiv.getText().contains(text1))
				isDisplayed=true;
			
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return isDisplayed;
	}
	
	public void clickCamerasHeading() throws IOException {
		try {
			reusableMethods.clickElement(driver, camerasHeading);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public void verifyCameraSearchBox() throws IOException {
		try {
			reusableMethods.verifyIfPresent(driver, searchCameras, "searchCameras");
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public void searchForCameraPlace() throws IOException {
		try {
			place = cameraLocations.get(1).getText().trim();
			searchCameras.sendKeys(place);
			searchCameras.clear();
			place = cameraLocations.get(0).getText().trim();
			searchCameras.sendKeys(place);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public boolean verifySearchedPlaceResult() throws IOException {
		boolean result = false;
		try {
			if(place.equals(cameraLocations.get(0).getText().trim()))
				result=true;
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return result;
	}
	
	public void clickOnSearchResult() throws IOException {
		try {
			reusableMethods.clickElement(driver, cameraLocations.get(0));
			reusableMethods.explicitWait(driver, popupDiv);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public boolean validateSearchedPlaceIsDisplayedOnMap() throws IOException {
		boolean result = false;
		try {
			reusableMethods.switchToNewIframe(driver, "ifCam");
			if(cameraDesc.getText().contains(place))
				result = true;
			reusableMethods.switchToDefaultFrame(driver);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return result;
	}
	
	public void clickOnTolls() throws IOException {
		try {
			reusableMethods.clickElement(driver, tollsHeading);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public void verifyTollSearchBox() throws IOException {
		try {
			reusableMethods.verifyIfPresent(driver, searchTolls, "searchTolls");
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public void searchForTollList() throws IOException {
		try {
			place = tollLocations.get(1).getText().trim();
			searchTolls.sendKeys(place);
			searchTolls.clear();
			place = tollLocations.get(0).getText().trim();
			searchTolls.sendKeys(place);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public boolean verifySearchedTollResult() throws IOException {
		boolean result = false;
		try {
			if(place.equals(tollLocations.get(0).getText().trim()))
				result=true;
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return result;
	}
	
	public void clickOnTollSearchResult() throws IOException {
		try {
			reusableMethods.waitForPageReady(driver);
			reusableMethods.clickElement(driver, tollLocations.get(0));
			reusableMethods.explicitWait(driver, popupDiv);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
	}
	
	public boolean validateSearchedTollIsDisplayedOnMap() throws IOException {
		boolean result = false;
		try {
			reusableMethods.switchToNewIframe(driver, "myDropdownList");
			if(tollName.getText().equalsIgnoreCase(place))
				result = true;
			reusableMethods.switchToDefaultFrame(driver);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return result;
	}
}
