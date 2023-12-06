# 3.5 obraz ubuntu:22.04 z Java w wersji 8 oraz Kotlinem

FROM ubuntu:22.04

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

