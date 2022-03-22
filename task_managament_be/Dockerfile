FROM amazoncorretto:11-alpine-jdk
COPY ./target/task_managament_be-1.0-SNAPSHOT.jar task_managament_be-1.0-SNAPSHOT.jar
COPY local.config.yml local.config.yml
RUN [ "java","-jar", "task_managament_be-1.0-SNAPSHOT.jar","db","migrate","local.config.yml"]
ENTRYPOINT [ "java","-jar", "task_managament_be-1.0-SNAPSHOT.jar","server","local.config.yml"]