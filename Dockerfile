FROM eclipse-temurin:21-jdk
COPY /target/githubrest-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java","-jar","app/githubrest-0.0.1-SNAPSHOT.jar"]