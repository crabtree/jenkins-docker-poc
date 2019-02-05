freeStyleJob("Seedjob") {
    scm {
        git {
            branch '*/master'
            remote {
                url 'https://github.com/digital-tree/jenkins-docker-poc.git'
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