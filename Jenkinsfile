pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                sh "mvn clean -e"
            }
        }
        stage('Compile') {
            steps {
                sh "mvn compile -e"
            }
        }
        stage('Test with Selenium Webdriver') {
            steps {
                sh "mvn test -e"
            }
        }
                
    }
}