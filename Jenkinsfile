pipeline {
    agent any
    stages {
        stage('Start Up Server') {
            steps {
                sh '''
                    cd server
                    docker-compose up -d
                    sleep 40s
                '''
            }
        }
        stage('Run Tests') {
            agent {
                docker {
                    image 'maven:3.5-alpine'
                }
            }
            steps {
                sh 'mvn install'
            }
        }
    }
    post {
        always {
                sh '''
                    cd server
                    docker-compose down
                '''
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}