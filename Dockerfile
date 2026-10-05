#
# Build stage - Uses Java 21 and Maven
#
FROM maven:3.9.7-eclipse-temurin-21-alpine AS build
LABEL maintainer="Arpit Gupta <gupta.arpit03@gmail.com>"
LABEL description="CRM Backend Application - Build Stage"

WORKDIR /app

# Copy pom.xml and download dependencies (better layer caching)
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests -B

#
# Runtime stage - Optimized Java 21 runtime
#
FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="Arpit Gupta <gupta.arpit03@gmail.com>"
LABEL description="CRM Backend Application - Runtime"

# Create non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring

# Set working directory
WORKDIR /app

# Copy the built artifact from build stage
COPY --from=build /app/target/crm-*.jar app.jar

# Change ownership to non-root user
RUN chown -R spring:spring /app

# Switch to non-root user
USER spring:spring

# Expose application port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# JVM options for container environment
ENV JAVA_OPTS="-XX:+UseContainerSupport \
  -XX:MaxRAMPercentage=75.0 \
  -XX:InitialRAMPercentage=50.0 \
  -XX:+UseG1GC \
  -XX:+UseStringDeduplication \
  -Djava.security.egd=file:/dev/./urandom"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
