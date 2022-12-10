FROM openjdk:11 AS main
ENV ARTIFACT_NAME=binance-websocket-0.0.1.jar
ENV APP_HOME=/app

WORKDIR /app
COPY build/libs/${ARTIFACT_NAME} .

ENTRYPOINT exec java -jar /app/${ARTIFACT_NAME}