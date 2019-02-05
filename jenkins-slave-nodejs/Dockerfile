FROM jenkins/jnlp-slave

ARG NODE_VERSION

USER root

RUN curl -sL https://deb.nodesource.com/setup_${NODE_VERSION}.x | bash - && \
    apt-get install -y nodejs

USER jenkins