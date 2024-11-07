FROM openjdk:21-jdk
WORKDIR /app
COPY app.jar app.jar  # app.jar을 Docker 이미지에 복사

EXPOSE 8080

ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD

ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

ENTRYPOINT ["java", "-jar", "app.jar"]
