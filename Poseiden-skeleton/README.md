# Poseidon

Poseidon is an application that aims to drive more transactions for institutional investors who buy and sell 
fixed income securities.

## Author

**Laura HABDUL**

## Technologies
 - Java 8
 - Maven 4.0.0 
 - MySql 8.0.
 - Spring Boot 2.3.5
 - Spring Security
 - Spring MVC
 - Spring Data JPA
 - Hibernate 5
 - Thymeleaf

## Getting Started
### Prerequisites

Please install the following tools:

 - Java 8
 - Maven 4.0.0 
 - MySql 8.0.

### Installing 

1. Install Java:

https://www.oracle.com/java/technologies/javase-jdk11-downloads.html

2. Install Maven:

https://maven.apache.org/install.html

3. Install MySql:

https://dev.mysql.com/downloads/mysql/

## Database

Poseidon deals with prod database named «poseidon» and test database named «poseidon_test ».

- The file **data.sql** ( in *"/src/main/resources"*) contains SQL instructions to create prod database.

- The file **data-test.sql** (in *"/src/test/resources"*) contains scrypt SQL to create "test" database and contains dummies data for tests.

## Testing

To get all the different reports in html format, you need to run either the `mvn site` command.
 This will get you a JaCoCo report, SureFire report as well as a SpotBugs report.
