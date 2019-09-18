def call(Map pipelineParams){

    pipeline {
        
        agent any
        
        environment {
            
            SREGISTRY_CLIENT=pipelineParams.client.toString()
            SREGISTRY_REGISTRY_BASE='http://nginx'
            SREGISTRY_REGISTRY_USERNAME=pipelineParams.username.toString()
            SREGISTRY_REGISTRY_TOKEN=pipelineParams.token.toString()
       }

        stages {
            stage('Build') {
                steps {
                    echo 'Building..'
                    sh 'singularity build ' + pipelineParams.image + ' ' + pipelineParams.recipe
                }
            }
            stage('Test') {
                steps {
                    echo 'Testing..'
                }
            }
            stage('Deploy') {
               steps {
                    echo 'Deploying....'
                    sh 'export SREGISTRY_CLIENT=' + pipelineParams.client
                    script{
                        if (pipelineParams.tag == ''){
                            sh 'sregistry push ' + pipelineParams.image + ' --name=' + pipelineParams.collection + '/' + pipelineParams.container
                        }
                        else {
                            sh 'sregistry push ' + pipelineParams.image + ' --name=' + pipelineParams.collection + '/' + pipelineParams.container + ':' + pipelineParams.tag                   
                        }
                    }
                }
            }
            stage('Remote SSH') {
                //sshCommand remote: remote, command: "sudo echo "Hello World" "
                steps {
                    sh 'ssh ' + pipelineParams.vm_user + '@' + pipelineParams.vm_ip + ' "export SREGISTRY_CLIENT=' + pipelineParams.client +'; export SREGISTRY_REGISTRY_BASE=http://154.114.37.153; sregistry pull ' + pipelineParams.collection + '/' + pipelineParams.container + '" '
                }
            }
        }
    }
}


