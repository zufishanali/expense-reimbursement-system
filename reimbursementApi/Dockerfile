FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD_STAGE

#We are copying all the files inside of this directory into the container directory
COPY ./ ./

RUN mvn clean package

CMD ["java", "-jar", "/target/reimbursementApi-0.0.1-SNAPSHOT.jar"]