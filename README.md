# Rock-paper-scissors-Game

* [How to play Game (Wikipedia)](https://en.wikipedia.org/wiki/Rock_paper_scissors)

## How to Run
* Execute tests:

  **mvn clean install**

* starting Application:

  **mvn spring-boot:run**

### Tools & Technologies

* [Java 8]
* [Spring boot]
* [Maven]
* [Lombok]
* [Swagger]
* [JUnit 5]
* [Mockito]
* [H2]


### Guides
Follow the link below to find the proper documentation:
* [API Swagger UI](/swagger-ui.html#)

Follow the Link to see DataBase Status
* [DataBase_H2](/h2-console/login.jsp)

### Author
This project has been implemented by **Abbas Payami** for Bol.com Code Challenge
[Contact me](payami2013@gmail.com)

## Notes :
* First, you can call this API for starting the game in postman
  [ curl --location 'http://localhost:8080/api/v1/games/start?playerOneName=Abbas' \
  --header 'Content-Type: application/json' \
  --data '{
  "playerOneName": "Abbas"
  }' ]

*  For moving and playing you can call this API in postman

   [ curl --location --request PUT 'http://localhost:8080/api/v1/games/1?move=ROCK'   ]


*  For getting the statistics of the game you can call this API

   [ curl --location 'http://localhost:8080/api/v1/games/statistics'  ]

