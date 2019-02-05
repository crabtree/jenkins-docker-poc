# Scalable CI/CD infrastructure in 15 minutes

Simple Jenkins configuration with dynamic slaves based on docker containers.

## Creating dedicated docker machine

```
$ docker-machine create -d virtualbox --virtualbox-cpu-count "4" --virtualbox-disk-size "50000" --virtualbox-memory "8192" jenkins
```

## Setting up jenkins master

Build docker image from Dockerfile present in `jenkins-master` subdirectory.

```
$ docker build -f jenkins-master\Dockerfile -t jenkins-master jenkins-master
```

Create docker volume for master

```
$ docker volume create jenkins_master
```

Run master node

```
$ docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_master:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkins-master
```

Configure master node

1. Setup master without installing plugins

2. Configure docker plugin Manage Jenkins > System > Cloud

- Docker Host URI `unix:///var/run/docker.sock`

## Setting up jenkins slave

Fetch base slave image with jnlp support

```
$ docker pull jenkins/jnlp-slave
```

Build `jenkins-slave-nodejs`

```
$ docker build -f jenkins-slave-nodejs\Dockerfile -t jenkins-salve-nodejs:8 jenkins-slave-nodejs
```

Configure docker slave using image `jenkins-slave-nodejs:8`, mark to pull image once.