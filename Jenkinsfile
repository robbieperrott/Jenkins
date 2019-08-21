pipeline {
    agent any
    
    environment {
        SREGISTRY_CLIENT='registry'
        SREGISTRY_REGISTRY_BASE='http://nginx'
        SREGISTRY_REGISTRY_USERNAME='robbieperrott'
        SREGISTRY_REGISTRY_TOKEN='4f7c7746494e340cdb31abef2a398276c6b1a771'
           
    }

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh 'singularity build test.simg Singularity.recipe'
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
                sh 'export SREGISTRY_CLIENT=registry'
                sh 'sregistry push test.simg --name=test2/test_for_rob'
            }
        }
    }
}
