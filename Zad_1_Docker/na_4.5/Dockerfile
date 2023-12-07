# 4.5 stworzyć przykład typu HelloWorld oraz uruchomienie aplikacji 
# przez CMD oraz gradle

# mozliwosc uruchomienia przez gradle run zapewnilem w wersji na. 4.0
# natomiast tutaj przecwiczylem multi-stage build,
# zeby zmniejszyc rozmiar wynikowego obrazu

FROM ubuntu:22.04 AS builder

WORKDIR /app

ENV DEBIAN_FRONTEND=noninteractive 

RUN rm /bin/sh && ln -s /bin/bash /bin/sh

RUN apt-get -y update
RUN apt-get -y install curl wget unzip zip

RUN curl -s "https://get.sdkman.io" | bash
RUN chmod a+x "$HOME/.sdkman/bin/sdkman-init.sh"
RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && \
    sdk install java 8.0.392-zulu && \
    sdk install kotlin

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && \
    sdk install gradle

COPY build.gradle .
COPY src src

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && \
    gradle build

FROM openjdk:8u342-jre

WORKDIR /app

ENV ARTIFACT_NAME=app.jar

COPY --from=builder /app/build/libs/$ARTIFACT_NAME .

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}