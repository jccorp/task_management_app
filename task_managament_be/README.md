# Task Management Back End

This project expose a REST API to manage
the creation of tasks, list, edit and remove them.


## Prerequisites
* Have Java 11 installed.
* Have Maven installed.
* Have Docker installed (For running the app in a container)

This microservice was developed with the Dropwizard framework. [more info](https://www.dropwizard.io/en/latest/)

### local.config.yml
In the file local.config.yml is where you configure the application.[more info](https://www.dropwizard.io/en/latest/manual/configuration.html).

In this file you should replace the database connection values, in my 
case, I'm using H2 DB.

To compile the code type the following command:

### `mvn clean package`

There is a migrations.xml file that is needed by liquibase to create the database table.[more info](https://liquibase.org/get-started/quickstart)

You will need to execute the following command:
### `java -jar target/task_managament_be-1.0-SNAPSHOT.jar db migrate local.config.yml`

After that, to run the application type:

### `java -jar target/task_managament_be-1.0-SNAPSHOT.jar server local.config.yml`

### Swagger
The app generates a UI that documents the endpoints and provides all the info to test them.

Depending on your configuration, the default value should deploy it in the following url:
* http://localhost:8080/swagger

### Reports
Unit test reports are under the site folder of this repo:
* surefire-report.html
* jacoco/index.html

## Docker

The application includes a Dockerfile to execute it in a
container.
To generate the image you should execute this command:

### `docker build --tag=task-management-be:latest .`

To execute the container type the following command:
### `docker run -d -p8080:8080 task-management-be:latest`
