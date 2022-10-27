FROM sbtscala/scala-sbt:openjdk-11.0.16_1.7.2_3.2.0 as build

WORKDIR /app
COPY project /app/project
COPY src /app/src
COPY build.sbt .

RUN sbt assembly

FROM flink:1.15.2-scala_2.12
COPY --from=build /app/target/scala-2.12/*.jar /opt/flink/usrlib/
