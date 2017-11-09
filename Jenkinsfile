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
        }
    } finally {
        junit '*/target/surefire-reports/*.xml'
    }

    stage ('Publish') {
        artifactoryServer.publishBuildInfo mavenBuildInfo
    }
}

node {
    stage ('Stage') {
        checkout scm
        sh 'echo "Hello!"'
    }
}
