FROM maven:3.6.3-jdk-11
WORKDIR  /opt/app
COPY pom.xml .
COPY src/ src/
RUN mvn package
EXPOSE 7076
CMD ["java", "-jar", "target/pet-0.0.1-SNAPSHOT.jar"]