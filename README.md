# Welcome to the Task Management App

This app has a Dropwizard microservice backend and a ReactJs app in the frontend.
You can add a new task and put a due date and keep track of your goals.

## Deployment in OCI (Oracle Cloud Infrastructure)
* Have an active account
* Create a free virtual machine
* Login in the virtual machine
* Install git
* Clone this repo
* Install Java
* Install Maven
* Install NodeJs
* Install Docker
* Open Ports 3000(Frontend) and 8080(Backend)
* Follow the instructions in the BE project to create and start the container. *
* Follow the instructions in the FE project to create and start the container.

*NOTE: In the dockerfile of the backend service, you must replace the first line:

`FROM amazoncorretto:11-alpine-jdk`

to this:

`FROM openjdk:11-oraclelinux8`
