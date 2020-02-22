package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import models.TestConfig;

public class ReusableMethods {

	public WebDriver driver;
	
	public WebDriver getDriver(TestConfig testConfig){
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
		}

		catch(Exception e){
			System.out.println(e);
			Logger.getLogger("Exception is" +e);
		}
		return driver;
	}
	
	public void launchPage(String url)
	{
		try {
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);
			driver.get(url);
			waitForPageReady();
		}
		catch(Exception e) {
			System.out.println(e);
			Logger.getLogger("Exception is" +e);
		}
		
	}
	
	public void waitForPageReady() {
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
        	System.out.println(e);
			Logger.getLogger("Exception is" +e);
        }
    }

	
	public void enterText(WebDriver driver,WebElement element, String text){
		try{
			element.sendKeys(text);
		}

		catch(Exception e){
			Logger.getLogger("Exception is" +e);
		}
	}

	public void clickElement(WebDriver driver,WebElement element){
		try{
			element.click();
		}

		catch(Exception e){
			Logger.getLogger("Exception is" +e);
		}

	}

	public void selectFromDropdown(WebDriver driver,WebElement element, String dropDownValue){
		try{
			Select options = new Select(element);
			options.selectByVisibleText(dropDownValue);
		}

		catch(Exception e){
			Logger.getLogger("Exception is" +e);
		}
	}

	public void scrollIntoView(WebDriver driver,WebElement element){
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", element);
		}

		catch(Exception e){
			Logger.getLogger("Exception is" +e);
		}
	}

	public WebElement detectHiddenElement(WebDriver driver, String xpath ){
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
			Logger.getLogger("Exception is" +e);
		}

		return element;
	}

	public void verifyIfPresent(WebElement element){
		
		boolean isPresent = false;
		
		try{
           if(element.isDisplayed()){
        	   isPresent = true;
           }
		}

		catch(Exception e){
			Logger.getLogger("Exception is" +e);
		}

		finally{
			Assert.assertTrue("Element is not present", isPresent);
		}
	}
	
	public void explicitWait(WebDriver driver, WebElement element){
		try{
			Wait<WebDriver> wait = new WebDriverWait(driver,10);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		
		catch(Exception e){
			Logger.getLogger("Exception is" +e);
		}
	}
}
