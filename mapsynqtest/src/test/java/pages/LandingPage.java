package pages;

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
	
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'rdCameras')]")
	WebElement camerasHeading;
	
	@FindBy(how = How.XPATH, using = "//a[contains(@class,'rdErp')]")
	WebElement tollsHeading;

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
	
	public void validateMapIsDisplayed()
	{
		try {
			reusableMethods.verifyIfPresent(driver, mapSection, "mapSection");
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is "+e);
		}
	}
	
	public void validateHeadersOnLeftTab()
	{
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
}
