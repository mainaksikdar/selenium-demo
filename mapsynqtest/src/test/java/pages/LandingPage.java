package pages;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class LandingPage {
	
	public WebDriver driver;

	public LandingPage(WebDriver driver) {
		try {
			this.driver=driver;
			PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
		}

		catch(Exception e) {
			Logger.getLogger("Exception is" +e);
		}
	}
}
