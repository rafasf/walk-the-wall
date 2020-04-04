FROM adoptopenjdk:11.0.6_10-jre-openj9-0.18.1
LABEL maintainer="Rafael"

# Non-root user
ENV GID 1001
ENV UID 1001
ENV APP_USER "walkwall"

ENV APP_FILE="walk-the-wall.jar"

WORKDIR /app

RUN groupadd -g $GID -o $APP_USER && \
    useradd -m -u $UID -g $GID -o -s /bin/bash $APP_USER

COPY ./target/walk-the-wall-*-standalone.jar ./$APP_FILE

RUN chown -R $GID:$GID /app
USER $GID

EXPOSE 3000

CMD ["sh", "-c", "java -jar ${APP_FILE}"]
