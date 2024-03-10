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

        environment {
            NEXUS = credentials('NEXUS')
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
//                    sh 'npm test'
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

            stage('Release Application') {
                when {
                    expression {
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {
                    sh 'echo ${TAG_NAME} >VERSION'
                    sh 'zip -r ${component}-${TAG_NAME}.zip *'
//                    sh 'zip -d ${component}-${TAG_NAME}.zip Jenkinsfile'
//                    sh 'curl -f -v -u admin:admin123 --upload-file ${component}-${TAG_NAME}.zip http://172.31.37.60:8081/repository/${component}/${component}-${TAG_NAME}.zip'
                    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 127710927797.dkr.ecr.us-east-1.amazonaws.com'
                    sh 'docker build -t ${component} .'
                    sh 'docker tag ${component}:latest 127710927797.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME}'
                    sh 'docker push 127710927797.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME}'
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