def call() {
    pipeline {

        agent {
            node {
                label 'workstation'
            }
        }

        options {
            ansiColor('xterm')
        }

        stages {

            stage('code quality') {
                steps {
                    sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.46.48:9000 -Dsonar.login=admin -Dsonar.password=admin123'
                }
            }

            stage('unit test cases') {
                steps {
                    sh 'echo Unit Test Cases'
                }
            }

            stage('checkmarx SAST scan') {
                steps {
                    sh 'echo Checkmarx SAST Scan'
                }
            }

            stage('checkmarx SCA scan') {
                steps {
                    sh 'echo Checkmarx SCA Scan'
                }
            }
        }

        post {
            always {
                cleanWs()
            }
        }
    }
}