
def call(Map pipelineParams){

    pipeline {
        //agent { label 'docker-slave-31c0522090a3' }    
        agent any
        
        environment {
            
            SREGISTRY_CLIENT=pipelineParams.client.toString()
            SREGISTRY_REGISTRY_BASE='http://nginx'
            SREGISTRY_REGISTRY_USERNAME=pipelineParams.username.toString()
            SREGISTRY_TOKEN=pipelineParams.token
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
        }
    }
}
