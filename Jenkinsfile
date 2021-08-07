def mainBranch = false

pipeline {
    agent any

    environment {
        PATH = "/opt/gradle/gradle-6.8/bin:$PATH"
        SLACK_CHANNEL = '#jenkins'
        TEAM_DOMAIN = 'black-postoffice'
        TOKEN_CREDENTIAL_ID = 'junghyungil-Slack'

    }

    stages {

        stage('Start') {
            steps {
                slackSend (channel: SLACK_CHANNEL, color: '#FFFF00', message: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", teamDomain: TEAM_DOMAIN, tokenCredentialId: TOKEN_CREDENTIAL_ID )
            }
        }

        stage('Git Checkout') {
            steps {
                checkout scm
                echo 'Git Checkout Success!'
            }
        }

        stage('Build') {
            steps {
                sh 'gradle clean build --exclude-task test --exclude-task asciidoctor'
                echo 'Build Success!'
            }
        }

        stage('Test') {
            steps {
                sh 'gradle test'
                echo 'Test Success!'
            }
        }

      stage('Deploy') {
              steps([$class: 'BapSshPromotionPublisherPlugin']) {
                  sshPublisher(
                      continueOnError: false, failOnError: true,
                      publishers: [
                          sshPublisherDesc(
                              configName: "black-postoffice-reverse-proxy",
                              verbose: true,
                              transfers: [
                                  sshTransfer(
                                      execCommand: "sh deploy.sh"
                                  )
                              ]
                          )
                      ]
                  )
              }
          }

    }

  post {
    success {
         slackSend (channel: SLACK_CHANNEL, color: '#00FF00', message: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", teamDomain: TEAM_DOMAIN, tokenCredentialId: TOKEN_CREDENTIAL_ID )
         }

    failure {
         slackSend (channel: SLACK_CHANNEL, color: '#F01717', message: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL})", teamDomain: TEAM_DOMAIN, tokenCredentialId: TOKEN_CREDENTIAL_ID )
         }
    }

}

