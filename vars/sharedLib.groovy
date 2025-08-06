def call()
{
    node('ci-server') {

        stage('code checkout') {
            sh 'env'
            echo "Code checkout"
            if (env.tag_name ==~ '.*') {
                checkout([$class           : 'GitSCM',
                          branches         : [[name: "refs/tags/${env.tag_name}"]],
                          userRemoteConfigs: [[url: 'https://github.com/pdevops78/expense-backend.git']]
                ])
            } else {
                checkout([$class           : 'GitSCM',
                          branches         : [[name: "*/${env.branch_name}"]],
                          userRemoteConfigs: [[url: "https://github.com/pdevops78/expense-${component}.git"]]
                ])
            }
        }
        if (env.tag_name ==~ '.*') {
            stage('Build Code') {
                echo "OK"

            }
            stage('Release Software') {
                echo "OK"
            }
        } else {
            stage('LintCode') {
                sh 'env'
                echo "OK"
            }
            if (env.branch_name != 'main') {
                stage('Unit test') {
                    echo "OK"
                }
                stage('Integration test') {
                    echo "OK"
                }
            } else {
                stage('code review') {
                    echo "OK"
                }
            }
        }
    }
}