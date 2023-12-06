# 3.0 obraz ubuntu z Pythonem w wersji 3.8

# Nie najlepsze rozwiazanie ze wzgledu na to, 
# iz software-properties-common doinstalowuje
# dodatkowe paczki, ktore powiekszaja rozmiar obrazu
# (wynikowy rozmiar to 218 MB)

FROM ubuntu

WORKDIR /app

ENV DEBIAN_FRONTEND=noninteractive 

RUN apt-get -y update && \
    apt-get install -y software-properties-common && \
    add-apt-repository -y ppa:deadsnakes/ppa && \
    apt install -y python3.8 && \
    add-apt-repository -y --remove ppa:deadsnakes/ppa && \
    apt-get purge -y software-properties-common && \
    apt-get autoremove -y


