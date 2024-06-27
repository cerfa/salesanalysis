FROM openjdk:17-jdk-slim

WORKDIR /app

ADD target/financial-analysis-1.0.0.jar  /app

ENTRYPOINT ["java"]

CMD ["-jar" ,"financial-analysis-1.0.0.jar", "-Dserver.port=8188"]

