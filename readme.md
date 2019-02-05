# Scalable CI/CD infrastructure in 15 minutes

Simple Jenkins configuration with dynamic slaves based on docker containers.

## Creating dedicated docker machine

```
$ docker-machine create -d virtualbox --virtualbox-cpu-count "4" --virtualbox-disk-size "50000" --virtualbox-memory "8192" jenkins
```

## Setting up jenkins master

Build docker image from Dockerfile present in `jenkins-master` subdirectory. Provided `Dockerfile` sets up default plugins and several init groovy scripts.

```
$ docker build -f jenkins-master\Dockerfile -t jenkins-master jenkins-master
```

Create docker volume for master to preserve persistence.

```
$ docker volume create jenkins_master
```

Run master node. Mount `/var/jenkins_home` to a volume and share docker service from host with container.

```
$ docker run -d -p 8080:8080 -p 50000:50000 -v jenkins_master:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkins-master
```

Configure master node.

1. Setup master without installing plugins

2. Configure docker plugin [Manage Jenkins > System > Cloud]

- Docker Host URI `unix:///var/run/docker.sock`

## Setting up jenkins slave(s)

Fetch base slave image with jnlp support.

```
$ docker pull jenkins/jnlp-slave
```

Build `jenkins-slave-nodejs` image. In following example, you must specify Node.js major version as an build argument (`NODE_VERSION`).

```
$ docker build -f jenkins-slave-nodejs\Dockerfile --build-arg NODE_VERSION=8 -t jenkins-salve-nodejs:8 jenkins-slave-nodejs
```

Configure docker slave using image `jenkins-slave-nodejs:8`, mark to pull image once.