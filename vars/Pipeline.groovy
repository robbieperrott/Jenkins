
def call(Map pipelineParams){

    pipeline {
        agent any
    
        environment {
            SREGISTRY_CLIENT=pipelineParams.client
            SREGISTRY_REGISTRY_BASE='http://nginx'
            SREGISTRY_REGISTRY_USERNAME=pipelineParams.username
            SREGISTRY_REGISTRY_TOKEN=pipelineParams.token
            
            IMG = pipelineParams.image
            RECIPE = pipelineParams.recipe
            COLLECTION = pipelineParams.collection
            CONTAINER = pipelineParams.container
            
     }

        stages {
            stage('Build') {
                steps {
                    echo 'Building..'
                    sh 'singularity build ' + IMG + ' ' + RECIPE
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
                    sh 'export SREGISTRY_CLIENT=' + SREGISTRY_CLIENT
                    sh 'sregistry push ' + IMG + ' --name=' + COLLECTION + '/' + CONTAINER
                }
            }
        }
    }
}
