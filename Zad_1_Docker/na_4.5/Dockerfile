# 4.5 stworzyć przykład typu HelloWorld oraz uruchomienie aplikacji 
# przez CMD oraz gradle

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
    sdk install kotlin && \
    sdk install gradle

COPY build.gradle src .

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && gradle build

FROM ubuntu/jre:edge

WORKDIR /app

ENV ARTIFACT_NAME=app.jar

WORKDIR $APP_HOME
COPY --from=builder /app/build/libs/$ARTIFACT_NAME .

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}