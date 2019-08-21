
def call(Map pipelineParams){

    pipeline {
        agent any
   
        environment {
            # Is this step necessary?
                
            SREGISTRY_CLIENT=pipelineParams.sr_client
            SREGISTRY_REGISTRY_BASE='http://nginx'
            SREGISTRY_REGISTRY_USERNAME=pipelineParams.sr_username
            SREGISTRY_REGISTRY_TOKEN=pipelineParams.sr_token
            
            IMG = pipelineParams.sr_image
            RECIPE = pipelineParams.sr_recipe
            COLLECTION = pipelineParams.sr_collection
            CONTAINER = pipelineParams.sr_container
            
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
