Feature: Mapsynq site being launched successfully
#Scenario: Validate the status code of the response after launching
#	Given user sends a request to mapsynq
#    And validates the status code of the response
#     
#     
#Scenario: Validate map is being displayed after launching
#	Given user launches the site
#	And validates that map is being displayed
	

#Scenario: Validate the headers on the left tab are appearing
#	Given user launches the site
#	And validates that the headers on the left tab are appearing properly
 
Scenario: Validate that user is able to select each of the incidents listed under the incidents tab
	Given user launches the site
	And validates that live is the current header which is active
	And incidents is the current tab which is active
	Then system displays the list of incidents
	And upon clicking each incident system displays the incident on the map