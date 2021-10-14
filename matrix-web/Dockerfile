FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN mkdir /config
RUN mkdir /logs
ARG JAR_FILE
#COPY /src/main/resources/application.yml /config/
#COPY /src/main/resources/bootstrap.yml /config/
COPY target/${JAR_FILE} app.jar
#COPY /target/matrix-web-1.0.0.jar app.jar
EXPOSE 9091
#ENV JAVA_OPTS="-server -Xms1024m -Xmx2048m -XX:MaxNewSize=1024M"
CMD java -jar app.jar
