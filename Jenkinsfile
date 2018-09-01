pipeline {
  agent any
  tools {
        maven 'Maven3'
        jdk 'Java8'
  }
  options {
    buildDiscarder logRotator(numToKeepStr: '10')
  }
  stages {
    stage('Clean') {
      steps {
        sh 'mvn clean'
      }
    }
    stage('Compile') {
      steps {
        sh 'mvn compile'
      }
    }
    stage('Install') {
      steps {
        sh 'mvn install -Dgpg.skip=true'
      }
    }
    stage('Archive') {
      steps {
        archiveArtifacts artifacts: 'target/*.jar', excludes: 'target/original*'
      }
    }
  }
}
