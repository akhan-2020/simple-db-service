#!/bin/sh

java -Djava.security.egd=file:/dev/./urandom -javaagent:/apm-dd/dd-java-agent-0.43.0.jar -jar $APP_JAR_PATH
