# Build stage
FROM maven:3.3-jdk-8
COPY src /home/app/src
COPY pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml clean package
RUN mvn -f /home/app/pom.xml datanucleus:enhance

# Package stage
# FROM openjdk:11-jre-slim
EXPOSE 8080
ENTRYPOINT ["mvn", "-f", "/home/app/pom.xml", "appengine:devserver"]