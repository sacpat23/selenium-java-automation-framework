pipeline {
    agent any

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
    }

    parameters {
        choice(
            name: 'TEST_SUITE',
            choices: ['SmokeTest', 'EcommerceOrderFlowTest', 'AllTests'],
            description: 'Choose the Selenium test suite to run.'
        )
    }

    environment {
        ECOM_HEADLESS = 'true'
    }

    stages {
        stage('Checkout and environment') {
            steps {
                checkout scm
                sh 'java -version && mvn -version'
            }
        }

        stage('Run Selenium tests') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'ecom-test-account',
                    usernameVariable: 'ECOM_USERNAME',
                    passwordVariable: 'ECOM_PASSWORD'
                )]) {
                    script {
                        if (params.TEST_SUITE == 'AllTests') {
                            sh 'mvn --batch-mode clean test'
                        } else {
                            sh 'mvn --batch-mode clean test -Dtest="$TEST_SUITE"'
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
        }
    }
}
