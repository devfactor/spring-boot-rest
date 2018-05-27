# Why yet another Spring Boot starter
The idea came when a friend asked for a starter Java app to which he could move his existing PHP-based REST API. As a Java developer without experience in Spring Boot, I wanted to learn about it.

This project has examples for many of the features used in modern applications:
- Spring Boot is very popular
- gradle
- Database access
- Unit tests
- Automatic REST API documentation
- Operations [friendly](https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready-endpoints)
- Making an external API call
- Logging
- Exception handling

# Software used
- [Spring Boot](https://projects.spring.io/spring-boot/)
- [gradle 4.6 with wrapper](https://gradle.org/releases/)
- [MariaDB](https://mariadb.org/) for the database interactions
- [Mockito](http://site.mockito.org/) for unit tests
- [Swagger 2](https://swagger.io/) for automatic REST API documentation
- [Lombok](https://projectlombok.org/) for automatic getters and setters
- [Typicode](https://jsonplaceholder.typicode.com/) provides Fake Online REST API for Testing and Prototyping

# Spring Boot features used in this starter
- [Spring Web MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html) for the REST API
- [Spring Test](https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html) to use Spring's testing features
- [Actuator](http://www.baeldung.com/spring-boot-actuators) jar added for metrics and other production-ready features, like health checks
- Logging - Spring Boot uses [logback](https://logback.qos.ch/) internally
- Custom exception handling

# SQL to create the table used by this app

```sql
drop table test.mydata;

create table test.mydata (
 id int not null,
 name varchar(200) not null,
 primary key(id)
);

insert into test.mydata values (1, 'one');
insert into test.mydata values (2, 'two');
insert into test.mydata values (3, 'three');
insert into test.mydata values (4, 'four');

commit;

select count(*)
from test.mydata;

select *
from test.mydata;
```

# To run the app
- Clone the project
- Look at property spring.datasource.url in application.properties
- Create a schema named test, or update the schema name to your own schema
- Run above SQL, changing schema name if needed
- cd to the project directory
- Clean, build the project, and run tests: `./gradlew clean build`
- Run the app: `java -jar build/libs/spring-boot-rest-0.1.0.jar`

# Try it out
- View the [Swagger REST API docs page](http://localhost:8080/swagger-ui.html)
- List numbers
  - `http://localhost:8080/myapp/numbers`
  - `curl -X GET -H "Accept: application/json" http://localhost:8080/myapp/numbers`
- Update one or more numbers
  - `curl -H 'Content-Type: application/json' -X PUT -d '{"numbers": [{"id": 1, "name": "Oneone"}]}' http://localhost:8080/myapp/numbers`
- Create one or more numbers
  - `curl -H 'Content-Type: application/json' -X POST -d '{"numbers": [{"id": 12, "name": "Twelve"}]}' http://localhost:8080/myapp/numbers`
- Delete a number
  - `curl -X DELETE http://localhost:8080/myapp/numbers/1`
- Call an external REST API
  - `curl -X GET -H "Accept: application/json" http://localhost:8080/myapp/externalApiCall/5`

# A note of thanks
Thanks to all developers out there who I continue to learn so much from.
