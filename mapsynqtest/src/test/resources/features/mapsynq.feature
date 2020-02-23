Feature: Mapsynq site being launched successfully
Scenario: Validate the status code of the response after launching
	Given user sends a request to mapsynq
    And validates the status code of the response
  
     
Scenario: Validate map is being displayed after launching
	Given user launches the site
	And validates that map is being displayed
	

Scenario: Validate the headers on the left tab are appearing
	Given user launches the site
	And validates that the headers on the left tab are appearing properly
 
 
Scenario: Validate that user is able to select each of the incidents listed under the incidents tab
	Given user launches the site
	And validates that live is the current header which is active
	And incidents is the current tab which is active
	Then system displays the list of incidents
	And upon clicking each incident system displays the incident on the map
	

Scenario: Validate that user is able to search for the places from among the camera list and click on it
	Given user launches the site
	And clicks on camera tab
	And verifies search box being displayed
	Then searches for a location from among the list
	And system displays only the specific location that has been searched
	Then user clicks on the search result
	And system displays the same on the map  


Scenario: Validate that user is able to search for tolls from the toll list and click on it
	Given user launches the site
	And clicks on tolls tab
	And verifies search box for tolls is being displayed
	Then searches for a location from among the toll list
	And system displays only the specific place that has been searched
	Then user clicks on the search result of the tolls
	And system displays the specific toll details on the map  