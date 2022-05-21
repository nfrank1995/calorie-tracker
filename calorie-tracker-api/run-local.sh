#!/bin/bash
echo "starte api auf port 8080"
./gradlew bootJar
java -jar build/libs/calorie-tracker-api-0.0.1-SNAPSHOT.jar