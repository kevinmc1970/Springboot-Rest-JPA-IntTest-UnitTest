Spring Data JPA
decided to create a basic app from scratch because getting errors trying to change the other projects

this is working!

1) includes working integration test - hits the endpoints and writes to test database
2) includes working unit test
3) includes Spring Data JPA - CrudRepository etc

use postman to send json string to localhost:8080/student/new
and browser to view localhost:8080/studentlist or localhost:8080/students/ID created above

when testing the test looks for DB config in test/resources then in the main/resources (if empty) 
so would need to make sure the test props contains an in-memory database - in this example it just creates another derby database
