FROM eclipse-temurin:21-jdk

EXPOSE 8086
# Copy your app files or JAR
ADD target/joblisting-automation-mongodb.jar joblisting-automation-mongodb.jar
ENTRYPOINT ["java", "-jar", "/joblisting-automation-mongodb.jar"]
