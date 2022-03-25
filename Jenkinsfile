pipeline {
    agent any
    parameters {
    string description: 'Un correo ficticio, con el formato correcto, para registrar el usuario que está comprando. Si el correo ya existe devolverá un error y fallará el pipeline.', name: 'CorreoRegistro', trim: true
    }
    stages {
        stage('Clean') {
            steps {
                sh "./mvnw clean -e"
            }
        }
        stage('Compile') {
            steps {
                sh "./mvnw compile -e"
            }
        }
        stage('Test with Selenium Webdriver') {
            steps {
                sh "./mvnw test -e -DcorreoRegistro=" + params.CorreoRegistro + ""
            }
        }
    }
}