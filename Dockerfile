FROM amazoncorretto:11
VOLUME /tmp
ARG JAR_FILE

ENV APP_JAR_PATH=/app/app.jar
COPY ${JAR_FILE} ${APP_JAR_PATH}
COPY scripts/docker_entrypoint.sh /app/entrypoint.sh

CMD chmod +x /app/entrypoint.sh
ENTRYPOINT ["/app/entrypoint.sh"]