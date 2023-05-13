# add docker-maven-plugin to pom.xml
# create docker image:
#   mvn package docker:build
# launch docker container based on image
#   mvn docker:start
# stop container
#   mvn docker:stop
FROM eclipse-temurin:17-jre
RUN mkdir /opt/app
ADD maven/${project.artifactId}-${project.version}.jar /opt/app/locations.jar
CMD ["java", "-jar", "/opt/app/locations.jar"]