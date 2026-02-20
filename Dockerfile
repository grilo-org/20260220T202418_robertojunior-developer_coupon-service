# ====== BUILD STAGE ======
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace
# Copy project files
COPY .mvn/ .mvn/
COPY mvnw mvnw
COPY pom.xml .
RUN chmod +x mvnw
# Pre-fetch dependencies (leverages Docker layer cache)
RUN ./mvnw -q -B -DskipTests dependency:go-offline
# Copy sources and build
COPY src/ src/
RUN ./mvnw -q -B -DskipTests package

# ====== RUNTIME STAGE ======
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Copy the fat jar built by Spring Boot repackage
ARG JAR_FILE=target/coupon-service-0.0.1-SNAPSHOT.jar
COPY --from=build /workspace/${JAR_FILE} app.jar
EXPOSE 8080
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
