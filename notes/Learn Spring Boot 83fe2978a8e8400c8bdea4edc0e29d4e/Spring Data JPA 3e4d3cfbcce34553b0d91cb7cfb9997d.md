# Spring Data JPA

- Allows you to convert a class to a database
- Suppose we have the following class
    
    ```java
    public class Student {
    		
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Integer age;
    
        public Student(Long id,
                       String firstName,
                       String lastName,
                       String email,
                       Integer age) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.age = age;
        }
    
        public Long getId() {
            return id;
        }
    
        public String getFirstName() {
            return firstName;
        }
    
        public String getLastName() {
            return lastName;
        }
    
        public String getEmail() {
            return email;
        }
    
        public Integer getAge() {
            return age;
        }
    
        public void setId(Long id) {
            this.id = id;
        }
    
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    
        public void setAge(Integer age) {
            this.age = age;
        }
    
        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
    ```
    
- We first need to mark the class as an Entity and then mark the primary by using @Id
    
    ```java
    @Entity(name = "Student")
    public class Student {
    
        @Id
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Integer age;
    		
    		....
    
    }
    ```
    
- Now when you run the application, the database will be created.

# Using a Sequence Generator for The Key

```java
@Entity(name = "Student")
public class Student {

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = javax.persistence.GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;

		.....

}
```

`allocationSize`: This indicates by how much the sequence increments each time. So in our case, it increments by 1.

`generator` name passed should be same as `name`.

In `SequenceGenerator` `name` refers to the name of the generator and `sequenceName` refers to the name of the sequence.

# Setting Up Column Properties

- We can use `@Column` annotation to set column properties. Refer to documentation for more column properties.
    
    ```java
    @Column(
            name = "id",
            updatable = false // cannot be updated
    )
    private Long id;
    @Column(
            name = "first_name",
            nullable = false, // cannot be null
            columnDefinition = "TEXT" // set column type to TEXT
    )
    private String firstName;
    @Column(
            name = "last_name",
            nullable = false, // cannot be null
            columnDefinition = "TEXT" // set column type to TEXT
    )
    private String lastName;
    @Column(
            name = "email",
            nullable = false, // cannot be null
            columnDefinition = "TEXT", // set column type to TEXT
            unique = true // cannot be duplicated
    )
    private String email;
    @Column(
            name = "age",
            nullable = false // cannot be null
    )
    private Integer age;
    ```
    

# Setting Up Unique Constraints

- If we go to the created database and go to the table and look at the keys, the columns marked unique will have a long key.
    
    ![Screenshot 2023-06-25 at 10.37.31 PM.png](Spring%20Data%20JPA%203e4d3cfbcce34553b0d91cb7cfb9997d/Screenshot_2023-06-25_at_10.37.31_PM.png)
    
- We can change this through table properties.
    
    ```java
    @Entity(name = "Student")
    @Table(
      name = "student",
      uniqueConstraints = {
        @UniqueConstraint(
          name = "student_email_unique",
          columnNames = "email"
        )
      }
    )
    public class Student {
    	...
    	@Column(
    	  name = "email",
    	  nullable = false, // cannot be null
    	  columnDefinition = "TEXT" // set column type to TEXT
    	)
    	private String email;
    	...
    }
    ```
    

# Repositories

- We need repositories to perform CRUD operations and execute custom queries against the database.

![Screenshot 2023-06-26 at 12.40.26 AM.png](Spring%20Data%20JPA%203e4d3cfbcce34553b0d91cb7cfb9997d/Screenshot_2023-06-26_at_12.40.26_AM.png)

1. First we need to create a repository for the entity and the repositories are interfaces.
2. These repositories can extend JPARepository, PagingAndSortingRepository, CrudRepository.
3. We need to pass in the type and the id (primary key is long in our student class)
    
    ```java
    package com.example.demo;
    
    import org.springframework.data.jpa.repository.JpaRepository;
    
    public interface StudentRepository extends JpaRepository<Student, Long> {
        
    }
    ```
    
4. Now we need a method in the main class to execute a query.
    
    ### `CommandLineRunner` Interface
    
    - This is an interface used to indicate that an application should run when it is contained within a Spring Application.  It contains a run method and Spring Boot will automatically call the run method of all beans implementing this interface after the application is loaded
    
    ```java
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
    	return args -> {
    		Student shevinu = new Student(
    			"Shevinu",
    			"Nawalage",
    			"shevinu.dev@gmail.com",
    			21
    		);
    		studentRepository.save(shevinu);
    	};
    }
    ```