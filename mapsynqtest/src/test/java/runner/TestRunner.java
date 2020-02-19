package runner;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(
		features = "./src/test/resources/features/mapsynq.feature",
		glue={"stepDefs"}, 
		dryRun = false	
		)

public class TestRunner {

}
