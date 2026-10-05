#!/bin/bash
# CRM Backend - Development startup script
# Loads environment variables and starts the application

set -a  # automatically export all variables
source .env.development
set +a

# Set Java 21 (for Mac with Homebrew)
export JAVA_HOME=/opt/homebrew/Cellar/openjdk@21/21.0.8/libexec/openjdk.jdk/Contents/Home
export PATH=$JAVA_HOME/bin:$PATH

echo "=== Starting CRM Backend Application ==="
echo "Java Version: $(java -version 2>&1 | head -n 1)"
echo "Database: $SPRING_DATASOURCE_URL"
echo "Profile: $SPRING_PROFILES_ACTIVE"
echo ""

./mvnw spring-boot:run
