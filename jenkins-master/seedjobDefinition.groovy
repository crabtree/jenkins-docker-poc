def seedjobRepository = 'https://github.com/digital-tree/jenkins-docker-poc.git'

freeStyleJob("seedjob") {
    label("master")
    scm {
        git {
            branch '*/master'
            remote {
                url seedjobRepository
            }
        }
    }
    triggers {
        scm 'H/15 * * * *'
    }
    steps {
        dsl {
            external('seedjob.groovy')
            removeAction('DELETE')
            ignoreExisting(false)
        }
    }
}