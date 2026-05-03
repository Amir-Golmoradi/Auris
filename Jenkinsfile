pipeline {
    agent any



    stages {
        stage('test') {
            steps {

                sh 'bash -c "docker --version"'
                sh 'bash -c "mvn --version"'
            }
        }
        stage('build jar ') {
            steps {
                sh 'bash -c "mvn clean"'
                sh 'bash -c "mvn package install"'
            }
        }
        stage('build image and container') {
            steps {
                sh 'bash -c " docker-compose down -v"'

                sh 'bash -c "docker-compose build"'


                sh 'bash -c " docker-compose up -d"'
            }
        }
        stage('stream logs for 180 seconds') {
             steps {
                  sh '''
                   timeout 180 docker-compose logs -f --no-color || true
                 '''
              }
        }


    }

    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}