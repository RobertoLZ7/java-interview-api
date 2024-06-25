# TalentReef Java API Interview exercise

## Description

This is my aproach to complete the interview exercise provided. I took the template proyect as the base so all the requirements and scripts should remain the same.

## Requirements

Java 17 -- Java can be acquired using [SDKMAN!](https://sdkman.io/)

## Running the Application

Start the server using Gradle:

```shell
./gradlew bootRun
```

See [Running your Application with Gradle](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/#running-your-application) for more information.

Execute tests using Gradle:

```shell
./gradlew test
```

## Additional Information

This API was build using an H2 in memory database. Meaning the database will we created and populated using the data.sql file found in this repo everytime the app runs.
To see the database console go to localhost:9000/h2-console once the app is running and log into the database. all the credentials are in the application.yml file
