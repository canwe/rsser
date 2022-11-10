# Build stage
FROM maven:3.3-jdk-8
COPY src /home/app/src
COPY pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
RUN mvn -f /home/app/pom.xml -s /usr/share/maven/ref/settings-docker.xml clean package
RUN mvn -f /home/app/pom.xml -s /usr/share/maven/ref/settings-docker.xml datanucleus:enhance
RUN mvn -f /home/app/pom.xml -s /usr/share/maven/ref/settings-docker.xml appengine:devserver_start
RUN mvn -f /home/app/pom.xml -s /usr/share/maven/ref/settings-docker.xml appengine:devserver_stop

# Package stage
# FROM openjdk:11-jre-slim
EXPOSE 8080
ENTRYPOINT ["mvn", "-f", "/home/app/pom.xml", "-s", "/usr/share/maven/ref/settings-docker.xml", "appengine:devserver"]