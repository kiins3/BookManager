# Stage 1: Build ứng dụng
FROM maven:3.9-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml và download dependencies (cache layer này)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code và build
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run ứng dụng
FROM tomcat:10.1-jdk17

# Xóa các webapp mặc định của Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy file .war từ stage build
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose port
EXPOSE 8080

# Tomcat tự start khi container chạy
CMD ["catalina.sh", "run"]