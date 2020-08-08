FROM java:8-jdk-alpine
WORKDIR /usr/app

# Copy backend jar file to the image filesystem.
COPY ./build/libs/server-0.0.1.jar /usr/app/
RUN sh -c 'touch server-0.0.1.jar'

# Expose and Run the server app.
EXPOSE 8000
ENTRYPOINT ["java", "-jar","server-0.0.1.jar"]