def call()
{
    node('ci-server') {

        stage('code checkout') {
            sh 'env'
            echo "Code checkout"
            if (env.tag_name ==~ '.*') {
                checkout([$class           : 'GitSCM',
                          branches         : [[name: "refs/tags/${env.tag_name}"]],
                          userRemoteConfigs: [[url: "https://github.com/pdevops78/roboshop-${component}.git"]]
                ])
            } else {
                checkout([$class           : 'GitSCM',
                          branches         : [[name: "*/${env.branch_name}"]],
                          userRemoteConfigs: [[url: "https://github.com/pdevops78/roboshop-${component}.git"]]
                ])
            }
        }
        if (env.tag_name ==~ '.*') {
            sh 'docker build -t 041445559784.dkr.ecr.us-east-1.amazonaws.com/expense-${component}:${TAG_NAME} .'
            print 'OK'
            stage('Build Code') {
                echo "OK"

            }
            stage('Release Software') {
                echo "OK"
                sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 041445559784.dkr.ecr.us-east-1.amazonaws.com'
                sh 'docker push 041445559784.dkr.ecr.us-east-1.amazonaws.com/expense-${component}:${TAG_NAME}'
            }
            stage('Deploy to Dev'){
                sh 'aws eks update-kubeconfig --name dev-eks'
                sh 'argocd login argocd-dev.pdevops78.online --username admin --password $(argocd admin initial-password -n argocd | head -1) --insecure --skip-test-tls --grpc-web'
                sh 'argocd app create ${component} --repo https://github.com/devps23/eks-helm-argocd-ingress.git --path chart --upsert --dest-server https://kubernetes.default.svc --dest-namespace default.svc --insecure  --grpc-web --values values/${component}.yaml'
                sh 'argocd app set ${component} --parameter appVersion=${TAG_NAME}'
                sh 'argocd app sync ${component}'
                print 'OK'
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