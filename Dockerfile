FROM alpine/git
WORKDIR /app
RUN git clone https://github.com/sandeepbs2016/benze-test.git 

FROM maven:3.5-jdk-8-alpine
WORKDIR /app
COPY --from=0 /app/benze-test /app 
RUN mvn install 

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=1 /app/target/benze-test-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java -jar benze-test-0.0.1-SNAPSHOT.jar"] 
