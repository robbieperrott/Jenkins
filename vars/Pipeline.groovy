
def call(Map pipelineParams){

    pipeline {
        agent any
        
        environment {
            // Is this step necessary?
                
            // SREGISTRY_CLIENT='registry'
            
            SREGISTRY_CLIENT= '\''+ pipelineParams.client + '\''
            // SREGISTRY_REGISTRY_BASE='http://nginx'
            // SREGISTRY_REGISTRY_USERNAME=pipelineParams.username
            // SREGISTRY_REGISTRY_TOKEN=pipelineParams.token
            
            // IMG = pipelineParams.image
            // RECIPE = pipelineParams.recipe
            // COLLECTION = pipelineParams.collection
            // CONTAINER = pipelineParams.container
            
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
                    //sh 'export SREGISTRY_CLIENT=registry'
                    sh 'export SREGISTRY_CLIENT=' + pipelineParams.client
                    sh 'sregistry push ' + pipelineParams.image + ' --name=' + pipelineParams.collection + '/' + pipelineParams.container
                }
            }
        }
    }
}
