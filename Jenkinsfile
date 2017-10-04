pipeline {
  agent any
  tools {
    maven 'M3'
    jdk 'jdk8'
  }
  stages {
    stage('Initialize') {
      steps {
        echo "PATH = "${PATH}"
        echo "M2_HOME = ${M2_HOME}"
      }
    }
  }
}
