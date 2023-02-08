pipeline {
    agent any
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    environment {
        SOURCECODE_JENKINS_CREDENTIAL_ID = 'user_donghun'
        SOURCE_CODE_URL = 'https://lab.ssafy.com/s08-webmobile1-sub2/S08P12B107.git'
        RELEASE_BRANCH = 'develop'
    }
    stages {

        stage('clone') {
            steps {
                git url: "$SOURCE_CODE_URL",
                    branch: "$RELEASE_BRANCH",
                    credentialsId: "$SOURCECODE_JENKINS_CREDENTIAL_ID"
                sh "ls -al"
            }
        }

        stage('backend dockerizing') {
            steps {
                sh "pwd"
                sh "docker build -t server ./server"
            }
        }

		stage('Deploy') {
            steps{
                sh 'docker-compose up -d --build'
                sh 'docker-compose ps'
            }
            post {
                success {
                    echo 'docker-compose success'
                }

                failure {
                    echo 'docker-compose failed'
                }
            }		
        }
    }
}
