# Scalable CI/CD infrastructure POC

Simple Jenkins configuration with dynamic slaves based on Docker containers. This repository contains all required files to setup simple Jenkins master and slaves for Node.js simple workloads. In `jenkins-master` directory you can find small plugins list and some groovy scripts for master pre-configuration. There is also a definition of job that will be used to seed further Jenkins jobs. Under `jenkins-slave-nodejs`, there is simple Dockerfile describing Jenkins slave for running Node.js jobs.

# How to start

First, I would suggest forking this repository, as you likely modify the `seedjob.groovy` file to describe there your pipelines. After forking this repository, please set the `seedjobRepository` variable in the file `jenkins-master/seedjobDefinition.groovy` to point to the fork. Of course, don't forget to push changes to your fork.

## Setup jenkins master

Build a docker image from `Dockerfile` present in `jenkins-master` subdirectory. Provided `Dockerfile` sets up default plugins and several groovy init scripts.

```
$ docker build -f jenkins-master\Dockerfile -t jenkins-master jenkins-master
```

Run Jenkins master node. Mount `/var/jenkins_home` to a volume and share docker service from the host with the container.

```
$ docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_master:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock --name jenkins-master jenkins-master
```

Issuing the command to run your Jenkins master, it will take some time the container to spin up. You can watch the progress following the log output by executing the command `docker logs -f jenkins-master`. You will find there the one-time password required during the first login into your Jenkins master. After a successful startup, you should be able to access the Jenkins master via your web browser hitting your docker host address and port 8080.

## Setup jenkins slave(s)

Build `jenkins-slave-nodejs` image. In the following example, you must specify Node.js major version as a build argument (`NODE_VERSION`).

```
$ docker build -f jenkins-slave-nodejs\Dockerfile --build-arg NODE_VERSION=8 -t jenkins-salve-nodejs:8 jenkins-slave-nodejs
```

## Create dedicated docker machine

Optionally, if you don't have access to any docker host, you can create a docker host machine using `docker-machine` and VirtualBox.

```
$ docker-machine create -d virtualbox --virtualbox-cpu-count "4" --virtualbox-disk-size "50000" --virtualbox-memory "8192" jenkins
```