import jenkins.model.*
import hudson.model.Node

Jenkins.instance.setNumExecutors(5)
Jenkins.instance.setLabelString("master")
Jenkins.instance.setMode(Node.Mode.EXCLUSIVE)