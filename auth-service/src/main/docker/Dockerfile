FROM livingobjects/jre8
VOLUME /tmp
ADD auth-service-1.0.0.jar api.jar
RUN bash -c 'touch /api.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/api.jar"]
