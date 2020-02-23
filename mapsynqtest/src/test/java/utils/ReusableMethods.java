package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cucumber.listener.Reporter;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import models.TestConfig;


public class ReusableMethods {

	public WebDriver driver;
	
	public WebDriver getDriver(TestConfig testConfig) {
		try{
			String browser = testConfig.getBrowser();
			
			if(browser.equalsIgnoreCase("chrome")) {
			
				DesiredCapabilities capability = DesiredCapabilities.chrome();
				ChromeDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.merge(capability);
				driver = new ChromeDriver(chromeOptions);
			}
			else if(browser.equalsIgnoreCase("firefox")) {
				DesiredCapabilities capability = DesiredCapabilities.firefox();
				FirefoxDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.merge(capability);
				driver = new FirefoxDriver(firefoxOptions);
			}
			else if(browser.equalsIgnoreCase("ie")) {
				DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
				InternetExplorerDriverManager.iedriver().setup();
				InternetExplorerOptions ieOptions = new InternetExplorerOptions();
				ieOptions.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				ieOptions.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				ieOptions.merge(capability);
				driver = new InternetExplorerDriver(ieOptions);
			}
			else if(browser.equalsIgnoreCase("headless")) {
				DesiredCapabilities capability = DesiredCapabilities.chrome();
				ChromeDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("headless");
				chromeOptions.merge(capability);
				driver = new ChromeDriver(chromeOptions);
			}
			else
				throw new Exception("Browser name does not match with any of the choices");
		}

		catch(Exception e){
			Reporter.addStepLog(e.getMessage());
		}
		return driver;
	}
	
	public void launchPage(String url) throws IOException
	{
		try {
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
			driver.get(url);
			waitForPageReady(driver);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}
		
	}
	
	public void waitForPageReady(WebDriver driver) throws IOException {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Exception e) {
        	Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
        }
    }

	
	public void enterText(WebDriver driver,WebElement element, String text) throws IOException{
		try{
			element.sendKeys(text);
		}

		catch(Exception e){
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}
	}

	public void clickElement(WebDriver driver,WebElement element) throws IOException{
		try{
			element.click();
		}

		catch(Exception e){
			//Reporter.addStepLog("Exception is " +e);
			//Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}

	}

	public void selectFromDropdown(WebDriver driver,WebElement element, String dropDownValue) throws IOException{
		try{
			Select options = new Select(element);
			options.selectByVisibleText(dropDownValue);
		}

		catch(Exception e){
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}
	}

	public void scrollIntoView(WebDriver driver,WebElement element) throws IOException{
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		}

		catch(Exception e){
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}
	}

	public WebElement detectHiddenElement(WebDriver driver, String xpath ) throws IOException{
		WebElement element=null;
		try{
			List<WebElement> elements= driver.findElements(By.xpath(xpath));
			for(int i=0;i<elements.size();i++) {
				int x= elements.get(i).getLocation().getX();
				if(x!=0) 
				{
					element=elements.get(i);
					break;
				}

			}		
		}

		catch(Exception e){
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}

		return element;
	}

	public void verifyIfPresent(WebDriver driver, WebElement element, String name) throws Exception{
		
		boolean isPresent = false;
		
		try{
           if(element.isDisplayed()){
        	   isPresent = true;
           }
		}

		catch(Exception e){
			Reporter.addStepLog("Issue with "+name+" element, Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
			
		}

		finally{
			Assert.assertTrue("Element is not present", isPresent);
		}
	}
	
	public void explicitWait(WebDriver driver, WebElement element) throws IOException{
		try{
			Wait<WebDriver> wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		
		catch(Exception e){
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}
	}
	
	public String captureScreenshot(WebDriver driver) {
		File destinationPath=null;
		try {
			File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + (new Date().getTime()) + ".png");
			FileUtils.copyFile(sourcePath, destinationPath);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
		}
		return destinationPath.toString();
	}
	
	public boolean validateIfActive(WebElement element, String classVal) throws IOException {
		boolean isActive = false;
		try {
			List<String> classes = Arrays.asList(element.getAttribute("class").split(" "));
			if(classes.contains(classVal))
				isActive = true;
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}
		return isActive;
	}
	
	public void switchToNewIframe(WebDriver driver, String frameId) throws IOException {
		try {
			driver.switchTo().frame(frameId);
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}
	}
	
	public void switchToDefaultFrame(WebDriver driver) throws IOException {
		try {
			driver.switchTo().defaultContent();
		}
		catch(Exception e) {
			Reporter.addStepLog("Exception is " +e);
			Reporter.addScreenCaptureFromPath(captureScreenshot(driver));
		}
	}
		   
}

