String requestBinUrl = 'https://requestb.in/19vtajb1'
String requestBinCommand = "curl ${requestBinUrl} -s -X POST -d"

node {
    def artifactoryServer = Artifactory.newServer url: env.ARTIFACTORY_URL, credentialsId: 'artifactory-uploader'
    def rtMaven = Artifactory.newMavenBuild()
    def mavenBuildInfo

    stage ('Checkout') {
        checkout scm
    }

    stage ('Configure') {
        rtMaven.tool = 'mvn-3.5.2'
        rtMaven.deployer releaseRepo: 'bins-release-local', snapshotRepo: 'bins-snapshot-local', server: artifactoryServer
        rtMaven.resolver releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot', server: artifactoryServer
        mavenBuildInfo = Artifactory.newBuildInfo()
    }

    try {
        stage ('Build') {
            rtMaven.run pom: 'pom.xml', goals: 'clean package', buildInfo: mavenBuildInfo
            sh "${requestBinCommand} 'build=success'"
        }
    } catch (exception) {
        sh "${requestBinCommand} 'build=failure'"
        throw exception
    }
    finally {
        junit '*/target/surefire-reports/*.xml'
    }

    stage ('Publish') {
        artifactoryServer.publishBuildInfo mavenBuildInfo
    }
}

node {
    stage ('Stage') {
        try {
            checkout scm

            sh 'docker-compose build'
            sh 'docker-compose up -d'

            sleep(60000)

            processorContainerid = sh(returnStdout: true, script: 'docker ps | grep message-processor | awk "{print $1}"').trim()

            String curlCommand = 'curl http://rabbitmq:8080/message -g -X POST -d'
            String firstPayload  = "{\"messageId\":1, \"timestamp\":1234, \"protocolVersion\":\"1.0.0\", \"messageData\":{\"mMX\":1234, \"mPermGen\":1234}}"
            String secondPayload = "{\"messageId\":2, \"timestamp\":2234, \"protocolVersion\":\"1.0.1\", \"messageData\":{\"mMX\":1234, \"mPermGen\":5678, \"mOldGen\":22222}}"
            String thirdPayload  = "{\"messageId\":3, \"timestamp\":3234, \"protocolVersion\":\"2.0.0\", \"payload\":{\"mMX\":1234, \"mPermGen\":5678, \"mOldGen\":22222, \"mYoungGen\":333333}"

            sh "docker exec -it ${processorContainerid} ${curlCommand} '${firstPayload}'"
            sh "docker exec -it ${processorContainerid} ${curlCommand} '${secondPayload}'"
            sh "docker exec -it ${processorContainerid} ${curlCommand} '${thirdPayload}'"

            processorLogs = sh(returnStdout: true, script: 'docker logs ${processorContainerid}').trim()

            boolean passed = true
            passed = processorLogs.contains(firstPayload) && passed
            passed = processorLogs.contains(secondPayload) && passed
            passed = processorLogs.contains(thirdPayload) && passed

            sh 'docker-compose stop'
            sh "${requestBinCommand} 'deploy=success'"
        } catch (exception) {
            sh "${requestBinCommand} 'deploy=failure'"
            throw exception
        }
    }
}
