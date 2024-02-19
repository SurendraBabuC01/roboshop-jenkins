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

            stage('Code Quality') {
                steps {
                    sh 'ls -l'
//                    sh 'sonar-scanner -Dsonar.projectKey=${component} -Dsonar.host.url=http://172.31.46.48:9000 -Dsonar.login=admin -Dsonar.password=admin123 -Dsonar.qualitygate.wait=true'
                    sh 'echo Code Quality'
                }
            }

            stage('Unit Test Cases') {
                steps {
                    sh 'echo Unit Test Cases'
                    sh 'python3.6 -m unittest'
                }
            }

            stage('Checkmarx SAST Scan') {
                steps {
                    sh 'echo Checkmarx SAST Scan'
                }
            }

            stage('Checkmarx SCA Scan') {
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