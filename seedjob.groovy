def helloWorldScript = '''
node {
  stage('hello') {
    echo 'Hello World'
  }
}
'''

pipelineJob("hello-world-inline") {
    description "Hello world job"
    definition {
        cps {
            script(helloWorldScript)
            sandbox(true)
        }
    }
}