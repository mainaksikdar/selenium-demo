package pages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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
	
	@FindBy(how = How.XPATH, using = "//label[contains(@class,'rdCameras')]")
	WebElement camerasHeading;
	
	@FindBy(how = How.XPATH, using = "//label[contains(@class,'rdErp')]")
	WebElement tollsHeading;
	
	@FindBy(how = How.XPATH, using = "//ul[@id='search_incident_singapore']/li")
	List<WebElement> individualIncident;
	
	@FindBy(how = How.CLASS_NAME, using = "popuptitle")
	WebElement popupTitle;
	
	@FindBy(how = How.ID, using = "popup_contentDiv")
	WebElement popupDiv;

	public LandingPage(WebDriver driver) {
		try {
			this.driver=driver;
			reusableMethods = new ReusableMethods();
			PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
		}

		catch(Exception e) {
			Logger.getLogger("Exception is" +e);
		}
	}
	
	public void validateMapIsDisplayed() {
		try {
			reusableMethods.verifyIfPresent(driver, mapSection, "mapSection");
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is "+e);
		}
	}
	
	public void validateHeadersOnLeftTab()	{
		try	{
			reusableMethods.verifyIfPresent(driver, directionsTab, "directionsTab");
			reusableMethods.verifyIfPresent(driver, personalTab, "personalTab");
			reusableMethods.verifyIfPresent(driver, liveTab, "liveTab");
			reusableMethods.verifyIfPresent(driver, incidentsHeading, "incidentsHeading");
			reusableMethods.verifyIfPresent(driver, camerasHeading, "camerasHeading");
			reusableMethods.verifyIfPresent(driver, tollsHeading, "tollsHeading");
		}
		catch (Exception e) {
			Logger.getLogger("Exception is "+e);
		}
	}
	
	public boolean validateLiveIsActive() {
		boolean isActive = false;
		
		try {
			isActive = reusableMethods.validateIfActive(liveTab, "tab_active");
		}
		catch(Exception e) {
			
		}
		
		return isActive;
	}
	
	public boolean validateIncidentsIsActive() {
		boolean isActive = false;
		try {
			isActive = reusableMethods.validateIfActive(incidentsHeading, "ui-state-active");
		}
		catch(Exception e) {
			
		}
		return isActive;
	}
	
	public boolean validateIncidentsAreDisplayed() {
		boolean incidentsShown=false;
		try {
			if(individualIncident.size()>=1)
				incidentsShown = true;
		}
		catch(Exception e) {
			
		}
		return incidentsShown;
	}
	
	public List<String> clickEachIncident() throws Exception{
		List<String> unclicked = new ArrayList<String>();
		try {
			for(WebElement incident : individualIncident)
			{
				reusableMethods.clickElement(driver, incident);
				if(validateIncidentDisplayedOnMap(incident)==false)
					unclicked.add(incident.getText());
			}
			
			if(unclicked.size()>0) 
				throw new Exception();
			
		}
		catch(Exception e) {
			unclicked.forEach(item->{
				Reporter.addStepLog(item+" is not clickable");
			});
			Reporter.addScreenCaptureFromPath(reusableMethods.captureScreenshot(driver));
		}
		return unclicked;
	}
	
	public boolean validateIncidentDisplayedOnMap(WebElement element) {
		boolean isDisplayed = false;
		try {
			reusableMethods.verifyIfPresent(driver, element, "popupDiv");
			String text1 = element.getText();
			String text2 = popupTitle.getText();
			if(!text2.equals("") && popupDiv.getText().contains(text1))
				isDisplayed=true;
			
		}
		catch(Exception e) {
			
		}
		return isDisplayed;
	}
}
