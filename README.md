 # README #

This is a very basic spring boot application containerized with docker to illustrate how we can create a micro service and deploy it with docker.

The application consists of two services, the Database and the API so it will create to different containers.

* The Database container is created from the official MySQL Docker image mysql:5.7.29
* The API container is created from a custom image which is assembled from a Dockerfile

## API Container ##

The API's container image is assembled from the following Dockerfile

```docker
FROM maven:3.5.4-jdk-8-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/hello-docker-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "hello-docker-0.0.1-SNAPSHOT.jar"]
```
#### What is this Dockerfile doing? ####

1. Pulls the maven:3.5.2-jdk-8-alpine image from docker-hub and starts the container. This is an image of alpine linux with maven installed
2. Copies the src folder and pom.xml file from the local working directory
3. Executes the mvn package command. This will download all project's dependencies, compile the code and create the hello-docker-0.1.0.jar file
4. Pulls the openjdk:8-jre-alpine image from docker-hub and starts the container. This is an image for alpine linux with openjdk installed  
5. Copies the generated hello-docker-0.0.1-SNAPSHOT.jar from maven container to /app/ of jdk container
6. Finally executes the command java -jar hello-docker-0.0.1-SNAPSHOT.jar which actually starts the application

## Database Container ##

Database container started from the official mysql:5.7.29 docker image

## How the whole application works ##

To make the whole application functional we have to compose it with `docker-compose` command, `docker-compose` command runs multi-container applications with Docker as they are defined in a `docker-compose.yml` file.
Our application uses the following `docker-compose.yml` which defines two services the `mysql` and the `api`. Each service has its own enviroment variables and ports mapping.

```docker
version: '3.7'
services:
    mysql:
        image: mysql:5.7.19
        container_name: Database
        restart: always
        environment:
          MYSQL_ROOT_PASSWORD: rootPassword
          MYSQL_DATABASE: tododb
          MYSQL_USER: todoUser
          MYSQL_PASSWORD: todoPassword        
#        volumes:
#            - db_data:/var/lib/mysql
        ports:
            - 3307:3306
    api:
        build: .
        container_name: API
        depends_on:
            - mysql
        environment:
            - SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/tododb?useUnicode=true&characterEncoding=utf8&useSSL=false
            - SPRING_DATASOURCE_USERNAME=todoUser
            - SPRING_DATASOURCE_PASSWORD=todoPassword
            - SPRING_JPA_HIBERNATE_DDL-AUTO=create-drop
            - APP_MYVARIABLE=Hello from docker
        ports:
            - 9090:9090
#volumes:
#    db_data: {}
```

## How to build ##
Download the project or better clone it

`git clone git@github.com:mxaris/hello-docker.git`

Inside the project's folder compose it with the following command:

```docker-compose up```

## How to test ##
Open the following URL in your browser, you must see a list of todos

[http://localhost:9090/todos](http://localhost:9090/todos)

with curl

`curl http://localhost:9090/todos | json_pp`

Create a new todo

`curl  --header "Content-Type: application/json"  -X POST -d '{"title":"Atlas dockerization","todo":"we must make Atlas dockerized"}' http://localhost:9090/todos | json_pp`

## Further testing ##
Make changes to the source code (eg add a new http request mapping in TodosController) and run the application with `--build api` option to rebuild the API's image

`docker-compose up --build`
