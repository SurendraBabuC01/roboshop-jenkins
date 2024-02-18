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
                    sh 'echo Code Quality'
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