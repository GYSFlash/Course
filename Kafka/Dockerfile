FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src

COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /src/producer/target/producer-1.0-SNAPSHOT.jar /app/producer.jar
COPY --from=build /src/consumer/target/consumer-1.0-SNAPSHOT.jar /app/consumer.jar

ENV RUN_APP=producer
ENV JAVA_TOOL_OPTIONS="-Xms256m -Xmx256m"

CMD sh -c 'if [ "$RUN_APP" = "consumer" ]; then exec java -cp /app/consumer.jar org.kafka.ConsumerMain; else exec java -cp /app/producer.jar org.kafka.ProducerMain; fi'