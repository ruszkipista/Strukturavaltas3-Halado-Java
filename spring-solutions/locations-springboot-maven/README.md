# Getting Started

run the application by executing `LocationsApplication.main()`

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)


# run application in docker containers
```docker
# create a network where the app and the db will communicate
docker network create --driver bridge locations-net

# run docker container with MariaDB
docker run -d \
  -e MYSQL_DATABASE=locations \
  -e MYSQL_USER=locations \
  -e MYSQL_PASSWORD=locations \
  -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
  -p 3396:3306 \
  --network locations-net \
  --name locations-mariadb-net \
  mariadb

# build and pack application using the Dockerfile file
mvn package

# run docker container with App
docker run -d \
  -e SPRING_DATASOURCE_URL=jdbc:mariadb://locations-mariadb-net/locations \
  -e SPRING_DATASOURCE_USERNAME=locations \
  -e SPRING_DATASOURCE_PASSWORD=locations \
  -p 8080:8080 
  --network locations-net \
  --name locations-app-net \
  locations-app
```
results in two running containers

connect to the application from browser:
`http://localhost:8080/`

connect to the db with a client:
`jdbc:mariadb://locations-mariadb-net/locations` `locations`@`locations`

cleanup:
```docker
# list docker containers
$ docker ps -a
CONTAINER ID   IMAGE           PORTS                                       NAMES
49568b2aa7d4   locations-app   0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   locations-app-net
757123f52c40   mariadb         0.0.0.0:3396->3306/tcp, :::3396->3306/tcp   locations-mariadb-net

# stop app container
$ docker stop 495
# remove app container
$ docker rm 495

docker stop 757
docker rm 757

# list docker images
$ docker images
REPOSITORY        TAG       IMAGE ID       SIZE
locations-app     latest    bb4ac8d5d6d3   318MB
mariadb           latest    4a632f970181   401MB
eclipse-temurin   17-jre    4315feef1604   266MB

# remove app image - not necessary if intended to run again
$ docker rmi bb4
# remove db image - not necessary if intended to run again
$ docker rmi 4a6
```