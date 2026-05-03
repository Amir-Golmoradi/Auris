# Use OpenJDK 21 as the base image
FROM azul/zulu-openjdk:21

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY ./target/auris-0.0.1-SNAPSHOT.jar app.jar

# Command to run the JAR file
ENTRYPOINT ["java",  "--enable-preview","-jar", "app.jar"]
