# Use Gradle with JDK 17 for build stage
FROM gradle:7.6-jdk17-alpine as builder
WORKDIR /build

# Only download dependencies if build.gradle or settings.gradle changes
COPY build.gradle settings.gradle /build/
RUN gradle build -x test --parallel

# Build the application
COPY . /build
RUN gradle build -x test

# Final runtime image
FROM openjdk:17.0-slim
WORKDIR /app

# Copy JAR file from builder
COPY --from=builder /build/build/libs/*-SNAPSHOT.jar ./app.jar

# Set appropriate permissions for non-root user
RUN chown nobody:nogroup /app

# Run as non-root user
USER nobody

# Expose application port
EXPOSE 8080

# Environment variables for JVM options
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Dsun.net.inetaddr.ttl=0"

# Application entrypoint
ENTRYPOINT ["java", "-jar", "$JAVA_OPTS", "app.jar"]
