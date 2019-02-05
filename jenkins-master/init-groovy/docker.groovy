import com.nirima.jenkins.plugins.docker.DockerCloud
import com.nirima.jenkins.plugins.docker.DockerTemplate
import com.nirima.jenkins.plugins.docker.DockerTemplateBase
import com.nirima.jenkins.plugins.docker.launcher.AttachedDockerComputerLauncher
import io.jenkins.docker.connector.DockerComputerAttachConnector
import jenkins.model.Jenkins

def nodejsSlaveParams = [
    bindAllPorts: false,
    bindPorts: '',
    cpuShares: null,
    dnsString: '',
    dockerCommand: '',
    environmentsString: '',
    extraHostsString: '',
    hostname: '',
    image: 'jenkins-salve-nodejs:8',
    macAddress: '',
    memoryLimit: null,
    memorySwap: null,
    network: '',
    privileged: false,
    pullCredentialsId: '',
    sharedMemorySize: null,
    tty: true,
    volumesFromString: '',
    volumesString: ''
]

// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerTemplateBase.java
DockerTemplateBase nodejsSlaveTemplateBase = new DockerTemplateBase(
    nodejsSlaveParams.image,
    nodejsSlaveParams.pullCredentialsId,
    nodejsSlaveParams.dnsString,
    nodejsSlaveParams.network,
    nodejsSlaveParams.dockerCommand,
    nodejsSlaveParams.volumesString,
    nodejsSlaveParams.volumesFromString,
    nodejsSlaveParams.environmentsString,
    nodejsSlaveParams.hostname,
    nodejsSlaveParams.memoryLimit,
    nodejsSlaveParams.memorySwap,
    nodejsSlaveParams.cpuShares,
    nodejsSlaveParams.sharedMemorySize,
    nodejsSlaveParams.bindPorts,
    nodejsSlaveParams.bindAllPorts,
    nodejsSlaveParams.privileged,
    nodejsSlaveParams.tty,
    nodejsSlaveParams.macAddress,
    nodejsSlaveParams.extraHostsString
)

// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerTemplate.java
DockerTemplate nodejsSlaveTemplate = new DockerTemplate(
    nodejsSlaveTemplateBase,
    new DockerComputerAttachConnector(),
    "nodejs", // labels
    "", // remoteFs
    "5" // instanceCapStr
)

def dockerCloudParameters = [
    connectTimeout: 3,
    containerCapStr: '10',
    credentialsId: '',
    dockerHostname: '',
    name: 'docker-1',
    readTimeout: 60,
    serverUrl: 'unix:///var/run/docker.sock',
    version: ''
]

// https://github.com/jenkinsci/docker-plugin/blob/docker-plugin-1.1.2/src/main/java/com/nirima/jenkins/plugins/docker/DockerCloud.java
DockerCloud dockerCloud = new DockerCloud(
    dockerCloudParameters.name,
    [
        nodejsSlaveTemplate
    ],
    dockerCloudParameters.serverUrl,
    dockerCloudParameters.containerCapStr,
    dockerCloudParameters.connectTimeout,
    dockerCloudParameters.readTimeout,
    dockerCloudParameters.credentialsId,
    dockerCloudParameters.version,
    dockerCloudParameters.dockerHostname
)

// get Jenkins instance
Jenkins jenkins = Jenkins.getInstance()

// add cloud configuration to Jenkins
jenkins.clouds.add(dockerCloud)

// save current Jenkins state to disk
jenkins.save()