pipeline {
  agent any
  tools {
    maven 'M3'
    jdk 'jdk8'
  }
  stages {
    stage('Initialize') {
      steps {
        echo "PATH = ${PATH}"
        echo "M2_HOME = ${M2_HOME}"
      }
    }
    stage('Build') {
      if(isUnix()) {
        sh "${M2_HOME}/bin/mvn -Dmaven.test.failure.ignore clean package"
      } else {
        bat("${M2_HOME}\bin\mvn -Dmaven.test.failure.ignore clean package")
      }
    }
  }
}
