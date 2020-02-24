package runner;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(
		features = "./src/test/resources/features/mapsynq.feature",
		glue={"stepDefs"}, 
		dryRun = false,
		plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"},
		tags={"@Smoke,@Regression"}
		)

public class TestRunner {

	 @AfterClass
	 public static void writeExtentReport() {
		 try {
			 Reporter.loadXMLConfig(new File("src/test/java/config/extent-config.xml"));
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
	 }
}
