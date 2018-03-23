FROM alpine:edge
MAINTAINER randykamradt@gmail.com
RUN apk add --no-cache openjdk8
COPY target/OAuth2Server.jar /opt/spring-cloud/lib/
ENV _JAVA_OPTIONS=-Xmx128m
ENV JEDIS_HOST_NAME=redis
ENV JEDIS_HOST_PORT=6379
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/spring-cloud/lib/OAuth2Server.jar"]
EXPOSE 8080
