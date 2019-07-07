# IntuitTest

##What's done:

Entity classes and main functionality (Services).

There are unit tests that illustrate how functionality works,
and the application starts (as a run job in  intellilJ), meaning all the dependencies are wired up correctly.
 
##Due to the time limit, I've had to stop here.

###What else needs to be done:

REST controllers for:
Registering citizens and candidates
Posting candidates' manifestos

Add proper storage (repositories) for entities within services

Email notifications - and perhaps I've overlooked some other requirements. 

I did not create any service interefaces - but we are safe to assume the Service classes are interfaces' 
implementation, and all public methods are the members of the said interfaces.

Some tests do too much - these could be split up to test individual cases.

We are missing tests for the controller - these could have used something like RESTEasy.
 
Certain methods within the service classes could have been optimised for performance.

Better error handling

Better @Autowired injections 
   
 
 


