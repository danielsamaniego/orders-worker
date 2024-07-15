# Order Worker

## Description
Order Worker is a microservice developed with Spring Boot and Gradle. This service manages work orders and is configured to interact with a MongoDB database.

## Prerequisites
- Docker
- Docker Compose
- JDK 21+

## How to Run

### Using Docker

Build and start the containers:
``` bash
docker-compose up –build
```

Verify the application is running at `http://localhost:8081`:
``` bash
curl http://localhost:8081
```

### Locally

Set the property values in the `src/main/resources/application.properties` file:
``` properties
spring.data.mongodb.host=localhost #If mongo is running locally or the IP of the server where mongo is running
spring.data.mongodb.port=27017 # Default port for MongoDB
```

Build the project:

``` bash
./gradlew build
```

Run the application:
``` bash
./gradlew bootRun
```
