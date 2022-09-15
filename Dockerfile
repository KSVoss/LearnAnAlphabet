FROM openjdk:18

ENV ENVIRONMENT=prod

LABEL maintainer="kai.s.voss@t-online.de"

ADD backend/target/LearnAnAlphabet.jar LearnAnAlphabet.jar

CMD [ "sh", "-c", "java -Dserver.port=$PORT -jar /LearnAnAlphabet.jar" ]
