FROM ubuntu:18.04

RUN useradd -ms /bin/bash user
VOLUME ["/home/newuser"]

ENV JAVA_VERSION  8.0.282-open
ENV SCALA_VERSION 2.12.13
ENV SBT_VERSION   1.5.5
ENV NODE_VERSION  v16.8.0

WORKDIR /root

RUN apt-get update && apt-get -y install \
    build-essential \
    curl \
    openjdk-8-jdk \
  && rm -rf /var/lbi/apt/lists/*

# install nvm for node and npm
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.38.0/install.sh | bash
ENV NVM_DIR=/root/.nvm
RUN . "$NVM_DIR/nvm.sh" && nvm install ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm use ${NODE_VERSION}
RUN . "$NVM_DIR/nvm.sh" && nvm alias default ${NODE_VERSION}
ENV PATH="/root/.nvm/versions/node/${NODE_VERSION}/bin/:${PATH}"
RUN node --version
RUN npm --version

# install newest sbt
RUN \
    curl -L -o sbt-$SBT_VERSION.deb https://scala.jfrog.io/artifactory/debian/sbt-{$SBT_VERSION}.deb && \
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

