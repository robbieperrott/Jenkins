def call(Map pipelineParams){

    pipeline {
        //agent { label 'docker-slave-31c0522090a3' }    
        agent any
        
        environment {
            
            SREGISTRY_CLIENT=pipelineParams.client.toString()
            SREGISTRY_REGISTRY_BASE='http://nginx'
            SREGISTRY_REGISTRY_USERNAME=pipelineParams.username.toString()
            SREGISTRY_REGISTRY_TOKEN=pipelineParams.token.toString()
       }

        stages {
            stage('Build') {
        
            }
            stage('Test') {
                steps {
                    echo 'Testing..'
                }
            }
            stage('Deploy') {
               
            }
        }
    }
    node {
        
        def remote = [:]
        remote.name = 'hons2019b'
        remote.host = '154.114.37.247'
        remote.user = 'ubuntu'
        remote.password = ''
        remote.allowAnyHosts = true
        
        stage('Remote SSH') {
            sshCommand remote: remote, command: "sudo echo "Hello World" "
        }
    }
}
