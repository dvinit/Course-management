# Course-management
Course-management-app is an app to manage a set of students who can enroll in a some courses and thus be graded in the courses according to their 
performance. This can be used by educators to manage their class of students. Users can create new students , courses. Then they can enroll students
in these courses and provide grades to the respective (students/course) pairs. 
Keywords : Spring-boot , Spring-boot JPA , Spring security , Object-relational-mapping.

Requirements to run this app: Java 17 , Maven.
To run , open terminal in root of the app and run:  mvn clean spring-boot:run
By default the app generates a few courses and students. 
Go to localhost:8080/h2 and connect to the database. Then query the students , courses , etc. 

Go to postman and try to register a new user by sending a post request to path: localhost:8080/user/register , include a body with a username and 
password in json format. Successful 201 Created response means user was created.  

To login , make a post request to path: localhost:8080/login while keeping the same password and user in the request body. On successful authentication,
copy the bearer token received in header of the response. This will be valid for 2 hrs and can be used to  make requests to the app.

To make requests to the app: Select authorization type as bearer-token. Use the bearer token from login response. Now we can make any requests we want. 

Example1: To get a user with id 1 :Send a get request to path: localhost:8080/user/1 with no body required. This should return a response similar to :
{
"id: : "some_id" ,
"name": "Student_name",
"birthDate: "some_date"
}
Other requests can be made similarly.See controllers in the code for all REST endpoints that are available. Users can enroll in some courses and be graded in those courses only of they were enrolled. Validations are implemented for every request. Courses , Students and users are connected data and their relationships are modelled accordingly.  
