Spring Data JPA (and validation and used for swagger testing)
decided to create a basic app from scratch because getting errors trying to change the other projects

this is working!

1) includes working integration test - hits the endpoints and writes to test database
2) includes working unit test - using MockMVC
3) includes Spring Data JPA - CrudRepository etc
4) ControllerAdvice and ResponseStatusException

use postman to send json string to localhost:8080/student/new
and browser to view localhost:8080/studentlist or localhost:8080/students/ID created above

when testing the test looks for DB config in test/resources then in the main/resources (if empty) 
so would need to make sure the test props contains an in-memory database - in this example it just creates another derby database

// using this to test JPA validation (Hibernate validation included in spring web starter) 
http://www.springboottutorial.com/spring-boot-validation-for-rest-services 
// so adding @Valid to registerStudentFor in controller - returns a 404 if not valid
Added 2 new postman tests - no description and description too short - both 'worked' - both returned the details of error too which tutorial said it would not
// got controlleradvice working - easy - add errror details class and class to overrider ResponseEntityExceptionHandler

// N.B. since spring 5 (I'm using 5+) controlleradvice superceded by ResponseStatusException - which is simply thrown as an exception in the controller
// I'll add to code to test - done works - need to hit /student/newError to get the reponse
