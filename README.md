# Principles of operation application APPLE MARKET

There are 4 endpoints in application, which enable cooperation with database.

PUT:
- add 5 random records
- add new record to database with JSON

GET:
- get specific number of records based on param in path
- if no indicated param in path, 3 records will be returned

To test endopoints: 
- When you build application, run Swagger localhost:8080/swagger-ui/#
  - > [Swagger Ui](http://localhost:8080/swagger-ui/#)
  - from the list choose: apple-bag-controller and then choose request HTTP
  - Click: Try it out, and then Execute
  - for /api/addBag you should modify JSON
  - for /api/forSale/{numberBags} you need to provide number of records

- The application is using H2 database, which is run locally: http://localhost:8080/h2-ui
  - > [H2 Console](http://localhost:8080/h2-ui)
  - At the beginning database is empty
  - in console you can monitor endpoints
  - after application is terminated database will be cleaned out

## Application based on Spring Boot, Maven, H2.
## Run Spring Boot application
```
mvn spring-boot:run
```
or run AppleMarketApplication.java in direct applemarket
