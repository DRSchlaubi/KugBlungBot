pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        withMaven(maven: 'M3', jdk: 'jdk8')
      }
    }
  }
}