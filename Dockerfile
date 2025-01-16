# Build stage
# FROM csanchez/maven:3.9.9-azulzulu-8-alpine
FROM ibmjava:8-sdk
COPY src /home/app/src
COPY entrypoint.sh /entrypoint.sh
COPY pom.xml /home/app/pom.xml
RUN chmod +x /entrypoint.sh
# RUN wget https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz
# RUN tar xf apache-maven-3.9.9-bin.tar.gz -C /opt
# RUN export MAVEN_HOME=/opt/apache-maven-3.9.9
# RUN export PATH=/opt/apache-maven-3.9.9/bin:${PATH}

ARG MAVEN_VERSION=3.9.9
ARG USER_HOME_DIR="/root"
# ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries
ARG BASE_URL=https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && wget ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz -O /tmp/apache-maven.tar.gz \
 && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN mvn -f /home/app/pom.xml dependency:resolve
RUN mvn -f /home/app/pom.xml clean package
RUN mvn -f /home/app/pom.xml datanucleus:enhance
RUN mvn -f /home/app/pom.xml appengine:devserver_start
RUN mvn -f /home/app/pom.xml appengine:devserver_stop

# Package stage
# FROM openjdk:11-jre-slim
EXPOSE 8080
ENTRYPOINT ["/entrypoint.sh"]