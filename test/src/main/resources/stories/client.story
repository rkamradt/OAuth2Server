Test out the client REST service
 
Narrative:
In order to remember information about a client
As a source of authorization
I want store, manipulate, and delete client information
     
Scenario:  Create a new client
 
Given a client named test that will require authorization
When the POST verb is issued to the server along with the name
Then a complete client with the name test is created and returned
 
Scenario:  Delete a client
 
Given a that a client named test no longer needs authentication
When the DELETE verb is issued along with the record id
Then all information about the client named test should be removed    
