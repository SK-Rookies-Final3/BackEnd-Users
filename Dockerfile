FROM openjdk:21-jdk
WORKDIR /app
COPY build/libs/app.jar app.jar  # 빌드된 JAR 파일 위치를 명확히 지정

EXPOSE 8080

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

ENTRYPOINT ["java", "-jar", "app.jar"]
