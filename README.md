# Simple CRUD API with Spring Boot and PostgreSQL
- This project was made to learn the fundamentals of Spring Boot. It's a simple CRUD API that allows users to manage a list of customers. The project helped me understand
  1. The fundamentals of Spring Boot.
  2. Dependency injection
  3. Spring MVC and N-Tier Architecture (DOA-Business-Service layers). Layering in web applications, separating concerns into the controller, service, and repository layers.
  4. Connecting to a database hosted on Docker at a local port using Spring Data JPA.

## How the code is organized
- Since this is a relatively small project, I didn't use seperate packages for the layers but they are seperated by classes.
- `project/docker-compose.yml`: Defines the configuration for a Docker container running a PostgreSQL database, with the database accessible at local port 5332. The actual database, user, etc. are created by the PostgreSQL server inside the Docker container when it starts.
- `project/src/main/java/com/shevinum`: Contains the class files 
 

## Technologies
- Languages      : Java, SQL
- Frameworks     : Spring (Spring Data), Spring Boot
- Developer Tools: Docker, PostgreSQL, Postman


 
