 Voting System REST API
-----------------------------
Voting system for deciding where to have lunch.
Spring Boot project with registration/authorization and
role-based access rights (USER, ADMIN). The administrator can
create/edit/delete a restaurant, menu, dish, and users
can vote for the restaurant they like depending on the menu.

Stack: [JDK 17](http://jdk.java.net/17/), Spring Boot 3.x, Spring Data JPA,
Spring Security, Spring Validator, Spring Cache, Lombok, H2, Swagger/Open API 3.0

-----------------------------

##  Technical requirement:
Design and implement a REST API using Hibernate/Spring/SpringMVC (Spring-Boot 
preferred!) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just 
a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote for a restaurant they want to have lunch at today
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

-----------------------------
[REST API documentation](http://localhost:8080/swagger-ui.html)  

Credenshells:
```
Test credenshells:
User:  user@yan.ru / 12345
Admin: user@ya.ru / 54321

```
-----------------------------
