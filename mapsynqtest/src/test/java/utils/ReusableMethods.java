package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import models.TestConfig;

public class ReusableMethods {

	public WebDriver driver;
	
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

	public void verifyIfPresent(WebDriver driver, WebElement element){
		
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
				
			}
			else if(browser.equalsIgnoreCase("ie")) {
				
			}
		}

		catch(Exception e){
			System.out.println(e);
			Logger.getLogger("Exception is" +e);
		}
		return driver;
	}
	
}
