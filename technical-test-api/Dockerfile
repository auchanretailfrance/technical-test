FROM maven:3.9.4-amazoncorretto-17

WORKDIR /app

COPY pom.xml .

RUN mvn clean install

COPY . .

ARG DEFAULT_PORT=80

ENV PORT $DEFAULT_PORT

EXPOSE $PORT

CMD ["mvn", "spring-boot:run"]
