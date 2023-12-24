# Use a base image with Java 11
FROM openjdk:11-jdk-slim as build

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory in the Docker image
WORKDIR /app

# Copy the Maven pom.xml and source code into the Docker image
COPY pom.xml .
COPY src/ src/
COPY data/ data/

# Build the application using Maven
# This step will download all the dependencies specified in your pom.xml
RUN mvn clean package -DskipTests

# Use a slim version of the Java 11 image for the final image
FROM openjdk:11-jdk-slim

# Copy the built jar file from the build stage
COPY --from=build /app/target/thesis-0.0.1-SNAPSHOT.jar /usr/app/thesis-0.0.1-SNAPSHOT.jar

# Copia la cartella 'data' nella directory /usr/app/ del container
COPY --from=build /app/data/ /usr/app/data/

# Set the working directory in the Docker image
WORKDIR /usr/app

# Expose the port the application runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java","-jar","thesis-0.0.1-SNAPSHOT.jar"]
