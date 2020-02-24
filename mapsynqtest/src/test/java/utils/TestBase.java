package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import models.TestConfig;

public class TestBase {
	
	private Properties prop;
	protected TestConfig testConfig;
	protected ReusableMethods reusableMethods;
	protected APIReusableMethods apiReusableMethods;
	public TestBase(){
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/test/java/config/config.properties");
			prop.load(ip);
			
			reusableMethods = new ReusableMethods();
			apiReusableMethods = new APIReusableMethods();
			testConfig = new TestConfig(prop.getProperty("browser"), prop.getProperty("url"), prop.getProperty("os"));
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
