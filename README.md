## Accounting API
***
Web API for accounting operations.
  
### Technologies
* Java 11
* Springboot
* Maven
* Junit 5
* API documentation with Swagger 
* JPA 
* Flyway for database migrations
* Docker for development environment
* Sonar for code quality

### Build and install
In root directory of the API, execute the command `mvn clean install`.

### Run the API
First of all, in root directory of the API, execute the docker-compose command `docker-compose up` to set up all containers required for the API. 
  
Then execute the command `mvn spring-boot:run` 

### API documentation - Swagger
Having API sucessfully executing in your machine, you can access its Swagger documention in the link http://localhost:8080/swagger-ui/index.html