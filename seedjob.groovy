def helloWorldScript = '''
node {
   echo 'Hello World'
}
'''

pipelineJob("hello-world") {
    description "Hello world job"
    definition {
        cps {
            script(helloWorldScript)
            sandbox(true)
        }
    }
}