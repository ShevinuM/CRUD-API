# Learn Spring Boot

Class: Java
Created: June 22, 2023 4:27 PM
Reviewed: No
Type: Section

# Technologies

## Apache Tomcat

- It’s a Java web application server.
- It is one of many embedded web servers for Spring Boot.

[Docker With PostgreSQL](Learn%20Spring%20Boot%2083fe2978a8e8400c8bdea4edc0e29d4e/Docker%20With%20PostgreSQL%20085dd9d9cbb94a56acc83e2aecfad11c.md)

# Writing A Basic Spring Boot Application

```java
package com.shevinum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
```

- By default, the application is running on port 8080
    
    `2023-06-22T16:25:16.603+05:30  INFO 19406 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''`
    
- We can use this port to communicate back and forth.

# Configuring Various Aspects of The Server

![Screenshot 2023-06-22 at 4.38.36 PM.png](Learn%20Spring%20Boot%2083fe2978a8e8400c8bdea4edc0e29d4e/Screenshot_2023-06-22_at_4.38.36_PM.png)

Within the resources folder, we can create `application.yml` to configure aspects of the server

- One aspect is changing the port of the server.

### Changing the port of the server

```yaml
server:
  port: 3000
```

### Running an application without a web server

- We can also run an application without a web server

```yaml
spring:
  main:
    web-application-type: none
```

- By default web application type is servlet.

Refer to the documentation for more.

# Creating a simple API

We first need to define a method as a REST endpoint.

- `@GetMapping('/')` defines the method as a get endpoint. `'/'` define the path. In this case it’s the root so if we run `[localhost:8080](http://localhost:8080)` we will get our method. If we want to change the path we can define something like `'/index'` so in this case, it will run on `[localhost:8080/index](http://localhost:8080/index)` .
- `@RestController` defines this class as a RESTful API.

```java
@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping('/')
    public String greet() {
        return "Hello World";
    }
}
```

### `@SpringBootApplication`

- It is `@Configuration` ,`@EnableAutoConfiguration`, and `@ComponentScan` annotations combined.
    - `@Configuration` is a part of the spring frameworks used to configure applications.
    - `@EnableAutoConfiguration` makes spring guess the configuration based on the JAR files available on classpath.
    - `@ComponentScan` responsible for telling spring where to look for components. By default spring will search within the package the main class is located.

# Spring Web MVC

- It is a framework that provides an easy way of implementing MVC architecture in the web applications.

## Components

### `@Controller`

- Marks the class as a web controller.

### `@RestController`

- Indicates that the class is a controller and all methods in the class will return a JSON response.
- It is `@Controller`and `@ResponseBody` annotations together.

### `@ResponseBody`

- It is a utility annotation that tells Spring to automatically serialize return values of the methods of the classes to HTTP responses.

### `@RequestBody`

- It is used to bind the HTTP request to the body of the controller.

### `@RequestMapping(method=Request.GET, value="/path)`

- This annotation specifies a method in the controller responsible for handling the request to the given endpoint.

### `@GetMapping(value="/path")`

- An abbreviated form of `@RequestMapping` .
- It’s specifically for HTTP get requests.

### `@PostMapping(value="/path")`

- It’s specifically for HTTP post requests.

### `@PutMapping(value="/path")`

- It’s specifically for HTTP put requests.

### `@DeleteMapping(value="/path")`

- It’s specifically for HTTP delete requests.

# JSON

```java
@SpringBootApplication
@RestController
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/index")
    public GreetResponse greet() {
        return new GreetResponse(
                "Hello World!",
                List.of("Java", "Kotlin", "Scala"),
                new Person("Shevinu")
        );
    }

    record Person(String name){}

    record GreetResponse(
            String greet,
            List<String> favProgrammingLang,
            Person person
    ){
    }
}
```

This is what gets returned

```jsx
// 20230623234013
// http://localhost:8080/index

{
  "greet": "Hello World!",
  "favProgrammingLang": [
    "Java",
    "Kotlin",
    "Scala"
  ],
  "person": {
    "name": "Shevinu"
  }
}
```

- Jackson library transforms the Java objects to JSON.

# Creating a PostgreSQL Database

1. Create a file called `docker-compose.yml` in the root folder.
2. Then use this as the base template
    
    ```yaml
    services:
      db:
        container_name: postgres
        image: postgres
        environment:
          POSTGRES_USER: shevinu
          POSTGRES_PASSWORD: shevinu123
          PGDATA: /data/postgres
        volumes:
          - db:/data/postgres
        ports:
          - "5332:5432"
        networks:
          - db
        restart: unless-stopped
    
      networks:
        db:
          driver: bridge 
      volumes:
        db:
    ```
    
3. Then add the following dependency in pom.xml to execute queries within java
    
    ```xml
    <dependency>
    	<groupId>org.postgresql</groupId>
    	<artifactId>postgresql</artifactId>
    	<scope>runtime</scope>
    </dependency>
    ```
    
4. Next we need to add the spring data JPA configuration
    
    ```xml
    <dependency>
    	<groupId>org.springframework.boot</groupId>
    	<artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    ```
    
5. Then we need to add the following under `spring` in application.yml
    1. `datasource`
        
        ```yaml
        spring:
          datasource:
            url: jdbc:postgresql://localhost:5432/customer
            username: user
            password: password
          main:
            web-application-type: servlet
        ```
        
        username, password, and host are the same as the usernames and passwords we provided in the docker-compose.
        
    2. `jpa:`
        
        ```yaml
        jpa:
        	hibernate:
        	  ddl-auto: create-drop
        	properties:
        	  hibernate:
        	    dialect: org.hibernate.dialect.PostgreSQLDialect
        	    format_sql: true
        	  show_sql: true
        ```
        
6. Execute SQL commands within terminal
    1. Type the following in terminal
        
        ```
        docker exec -it postgres bash
        ```
        
    2. Connect to the database by providing the username
        
        ```
        psql -U shevinu
        ```
        
    3. List the databases if you need
        
        ```
        \l
        ```
        
7. Creating a database
    
    ```
    CREATE DATABASE customer;
    ```
    
8. At any point if you need to come out of psql or container, press Ctrl + D. To connect again repeat from step 6.

## Removing a Docker Container and Volume

1. Stop the Docker Compose services using the following command:
    
    ```
    docker-compose down
    ```
    
2. To remove the Docker volume, you first need to identify it. You can list all Docker volumes using the following command:
    
    ```
    docker volume ls
    ```
    
    Look for a volume that corresponds to your PostgreSQL service. The name will likely include the name of your project directory and the name you specified for the volume in your **`docker-compose.yml`** file.
    
3. Once you have identified the correct volume, you can delete it using the following command (replace **`your_volume_name`** with the actual name of the volume):
    
    ```
    docker volume rm your_volume_name
    ```
    

[Spring Data JPA](Learn%20Spring%20Boot%2083fe2978a8e8400c8bdea4edc0e29d4e/Spring%20Data%20JPA%203e4d3cfbcce34553b0d91cb7cfb9997d.md)

# Implementing GET Requests

- Let’s implement functionality so that we can read all the customers in the database.
1. First, we need to create a method in the main application that acts as an endpoint for get requests for getting customers.
    
    ```java
    @SpringBootApplication
    @RestController
    public class Main {
        public static void main(String[] args) {
            SpringApplication.run(Main.class, args);
        }
    
        @GetMapping("/getCustomers")
        public List<Customer> getCustomers() {
            return List.of();
    
    }
    ```
    
2. Till now, if we access /getCustomers we just get an empty list. To get the actual list of customers we need to connect it to the repository. 
    
    ```java
    @SpringBootApplication
    @RestController
    public class Main {
    
        private CustomerRepository customerRepository;
    
        public Main(CustomerRepository customerRepository) {
            this.customerRepository = customerRepository;
        }
    
        public static void main(String[] args) {
            SpringApplication.run(Main.class, args);
        }
    
        @GetMapping("/getCustomers")
        public List<Customer> getCustomers() {
            return customerRepository.findAll();
    
    }
    ```
    
3. Now you can use postman to test the GET request `localhost:8080/getCustomers`.

# Implementing POST Requests

- In this project we are going to capture the request from the body.
    
    ```java
    record CustomerRequest(
            String name,
            String email,
            Integer age
    ){
    }
    
    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = new Customer(
                customerRequest.name(),
                customerRequest.email(),
                customerRequest.age()
        );
        customerRepository.save(customer);
    }
    ```
    
    - **`@RequestBody CustomerRequest customerRequest`:** tells Spring that the information of the new customer you're trying to add should be taken from the body of the HTTP request. It converts the JSON from the request body into a **`CustomerRequest`** object.
- We can now use Postman to test the API.

# Implementing DELETE Requests

```java
@DeleteMapping("/deleteCustomer/{id}")
public void deleteCustomer(@PathVariable("id") Integer id) {
    customerRepository.deleteById(id);
}
```

- [localhost:8080/deleteCustomer/1](http://localhost:8080/deleteCustomer/1) will delete customer with id 1.

# Implementing UPDATE Requests

- We use PUT to update existing records

```java
@PutMapping("/updateCustomer/{id}")
    public void updateCustomer(@PathVariable("id") Integer id,
                               @RequestBody CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        Customer customer = optionalCustomer.get();
        customer.setName(customerRequest.name());
        customer.setEmail(customerRequest.email());
        customer.setAge(customerRequest.age());
        customerRepository.save(customer);
    }
```

- Here, we take the customer id like we did when deleting and then we take the object from the body and update the customer.
- `.findById(id)` returns an optional object so we need to use .get() to obtain the customer.

# Structuring The Code

- When we create a web app in Spring Boot we need to adhere to MVC architecture and DAO-Service-Controller-View architecture.
- DAO-Service-Controller-View structure is a more detailed version of MVC. In this structure, the "Model" of MVC is broken down into the DAO and Service layers. The Controller and View remain the same in both structures.

## MVC Architecture

This is a design pattern that's often used in web applications.

- **Model**: Represents the data and the business logic of the application.
- **View**: Presents the data to the user. In a web application, this could be the HTML/CSS/JavaScript that makes up the web page.
- **Controller**: Takes user input from the View, updates the Model, and then updates the View to reflect any changes in the Model.

## **DAO-Service-Controller-View**

This is a typical layering structure for a web application.

- **DAO (Data Access Object)**: This layer interacts with the storage system (like a database) to fetch, store, and update data.
- **Service (or Business Layer)**: This layer contains business logic that operates on the data. It uses the DAO layer to get the data it needs.
- **Controller:** Similar to the Controller in MVC, it takes user input, calls the appropriate Service methods, and sends data to the View.
- **View:** Similar to the View in MVC, it presents data to the user.

- There should ideally be 5 packages in the Spring Boot project which contain each of the following type of classes.
    1. API Layer 
    2. Business Layer
    3. DAO Layer
    4. DTO Classes
    5. Entity Classes
- Here's a basic guide on how to structure your Spring Boot application into API, DAO, and Business layers:
    1. **API Layer (Controller Classes):** This layer handles HTTP requests and responses. It uses the **`@Controller`** or **`@RestController`** annotation. Typically, these classes should not contain any business logic. They should only be responsible for receiving the request, calling the appropriate service method, and returning the response. They use **`@GetMapping`**, **`@PostMapping`**, **`@PutMapping`**, **`@DeleteMapping`** annotations for defining the endpoints.
        
        ```java
        @RestController
        public class CustomerController {
            private final CustomerService customerService;
        
            public CustomerController(CustomerService customerService) {
                this.customerService = customerService;
            }
        
            @GetMapping("/getCustomers")
            public List<Customer> getCustomers() {
                return customerService.getCustomers();
            }
        
            @PostMapping("/addCustomer")
            public void addCustomer(@RequestBody CustomerRequest customerRequest) {
                customerService.addCustomer(customerRequest);
            }
        
            @DeleteMapping("/deleteCustomer/{id}")
            public void deleteCustomer(@PathVariable("id") Integer id) {
                customerService.deleteCustomer(id);
            }
        
            @PutMapping("/updateCustomer/{id}")
            public void updateCustomer(@PathVariable("id") Integer id,
                                       @RequestBody CustomerRequest customerRequest) {
                customerService.updateCustomer(id, customerRequest);
            }
        }
        ```
        
    2. **Business Layer (Service Classes):** This layer contains the business logic. It uses the **`@Service`** annotation. Service classes process data, apply business rules, and interact with the DAO layer to fetch or store data.
        
        ```java
        @Service
        public class CustomerService {
            private final CustomerRepository customerRepository;
        
            public CustomerService(CustomerRepository customerRepository) {
                this.customerRepository = customerRepository;
            }
        
            public List<Customer> getCustomers() {
                return customerRepository.findAll();
            }
        
            public void addCustomer(CustomerRequest customerRequest) {
                Customer customer = new Customer(
                        customerRequest.name(),
                        customerRequest.email(),
                        customerRequest.age()
                );
                customerRepository.save(customer);
            }
        
            public void deleteCustomer(Integer id) {
                customerRepository.deleteById(id);
            }
        
            public void updateCustomer(Integer id, CustomerRequest customerRequest) {
                Optional<Customer> optionalCustomer = customerRepository.findById(id);
                if(optionalCustomer.isPresent()){
                    Customer customer = optionalCustomer.get();
                    customer.setName(customerRequest.name());
                    customer.setEmail(customerRequest.email());
                    customer.setAge(customerRequest.age());
                    customerRepository.save(customer);
                } else {
                    // Handle the case where the customer was not found
                }
            }
        }
        ```
        
    3. **DAO Layer (Repository Interfaces):** This layer interacts with the database or any other storage system. It uses the **`@Repository`** annotation or extends repository interfaces from Spring Data JPA like **`JpaRepository`**, **`CrudRepository`**, etc.
        
        ```java
        public interface CustomerRepository extends JpaRepository<Customer, Integer> {
        
        }
        ```
        
    4. **DTO (Data Transfer Object Classes):** These classes are used to structure the data that's transferred between client and server.
        
        ```java
        public record CustomerRequest(String name, String email, Integer age) { }
        ```
        
    5. **Model (Entity Classes):** These classes represent the objects you're storing in your database. They use annotations like **`@Entity`**, **`@Table`**, **`@Id`**, etc.
        
        ```java
        package com.shevinum;
        
        import jakarta.persistence.*;
        
        import java.util.Objects;
        
        @Entity
        public class Customer {
        
            @Id
            @SequenceGenerator(
                    name = "customer_sequence",
                    sequenceName = "customer_sequence",
                    allocationSize = 1
            )
            @GeneratedValue(
                    generator = "customer_sequence",
                    strategy = GenerationType.SEQUENCE
            )
            private Integer id;
            private String name;
            private String email;
            private Integer age;
        
            public Customer(String name, String email, Integer age) {
                this.name = name;
                this.email = email;
                this.age = age;
            }
        
            public Customer(){
        
            }
        
            public Integer getId() {
                return id;
            }
        
            public String getName() {
                return name;
            }
        
            public String getEmail() {
                return email;
            }
        
            public Integer getAge() {
                return age;
            }
        
            public void setId(Integer id) {
                this.id = id;
            }
        
            public void setName(String name) {
                this.name = name;
            }
        
            public void setEmail(String email) {
                this.email = email;
            }
        
            public void setAge(Integer age) {
                this.age = age;
            }
        
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Customer customer = (Customer) o;
                return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(age, customer.age);
            }
        
            @Override
            public int hashCode() {
                return Objects.hash(id, name, email, age);
            }
        
            @Override
            public String toString() {
                return "Customer{}";
            }
        }
        ```
        
- Apart from these layers, the application needs a main class seperately
    
    ```java
    @SpringBootApplication
    public class Main {
        public static void main(String[] args) {
            SpringApplication.run(Main.class, args);
        }
    }
    ```