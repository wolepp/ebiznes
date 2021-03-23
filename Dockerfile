FROM ubuntu:18.04

RUN useradd -ms /bin/bash user
VOLUME ["/home/newuser"]

ENV JAVA_VERSION  8.0.282-open
ENV SCALA_VERSION 2.12.13
ENV SBT_VERSION   1.4.9

WORKDIR /root

RUN apt-get update && apt-get -y install \
    build-essential \
    curl \
    openjdk-8-jdk \
  && rm -rf /var/lbi/apt/lists/*

# install newest npm (nodejsc package contains also npm)
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash - && \
    apt install -y nodejs

# install newest sbt
RUN \
    curl -L -o sbt-$SBT_VERSION.deb https://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
    dpkg -i sbt-$SBT_VERSION.deb && \
    rm sbt-$SBT_VERSION.deb && \
    apt-get update && \
    apt-get install sbt && \
    sbt sbtVersion

# install scala binaries for running in terminal
RUN \
    curl -L -o scala-$SCALA_VERSION.tgz https://downloads.lightbend.com/scala/$SCALA_VERSION/scala-$SCALA_VERSION.tgz && \
    mkdir /usr/local/share/scala && \
    tar -xzf scala-$SCALA_VERSION.tgz -C /usr/local/share && \
    rm scala-$SCALA_VERSION.tgz

ENV SCALA_HOME=/usr/local/share/scala-$SCALA_VERSION
ENV PATH="${SCALA_HOME}/bin:${PATH}"

USER user
WORKDIR /home/newuser

# port for scala play
EXPOSE 9000

# port for react
EXPOSE 3000

