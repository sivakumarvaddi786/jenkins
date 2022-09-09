pipeline {
 
    environment {
        
        //put your own environment variables
        REGISTRY_URI = 
}
 
    stages {
        stage('Initial Notification') {
            steps {
                 //put webhook for your notification channel 
                 echo 'Pipeline Start Notification'
            }
        }
stage('Code Analysis') {           
            steps {
               //put your code scanner 
                echo 'Code Scanning and Analysis'
            }
        }
 
        stage('Robot Testing') {
            steps {
                //put your Testing
                echo 'Robot Testing Start'
            }
            post{
                success{
                    echo "Robot Testing Successfully"
                }
                failure{
                    echo "Robot Testing Failed"
                }
            }
        }
stage("Build"){
            steps {
steps {withCredentials([usernamePassword(credentialsId: 'YOUR_ID_DEFINED', passwordVariable: 'YOUR_PW_DEFINED', usernameVariable: 'YOUR_ACCOUNT_DEFINED')]) {
                    sh """
                    docker login ${REGISTRY_URI} -u ${YOUR_ACCOUNT_DEFINED} -p ${YOUR_PW_DEFINED}
                    """
                }
echo "Docker Build"
sh """
                docker build -t ${IMAGE_NAME}:${VERSION_PREFIX}${BUILD_NUMBER} ${WORKSPACE} -f Dockerfile
                """
echo "Docker Tag"
sh """
                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${REGISTRY_URI}/${REGISTRY_NAME}/${IMAGE_NAME}:${GIT_BRANCH}-${GIT_COMMIT}
                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${REGISTRY_URI}/${REGISTRY_NAME}/${IMAGE_NAME}:${GIT_BRANCH}-${BUILD_NUMBER}
                    docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${REGISTRY_URI}/${REGISTRY_NAME}/${IMAGE_NAME}:${GIT_BRANCH}-${LATEST}
                """
                
                echo "Docker Push"
sh """
                    docker push ${REGISTRY_URI}/${REGISTRY_NAME}/${IMAGE_NAME}:${GIT_BRANCH}-${GIT_COMMIT}
                    docker push ${REGISTRY_URI}/${REGISTRY_NAME}/${IMAGE_NAME}:${GIT_BRANCH}-${BUILD_NUMBER}
                    docker push ${REGISTRY_URI}/${REGISTRY_NAME}/${IMAGE_NAME}:${GIT_BRANCH}-${LATEST}
                """
 
            }
            post{
                success{
                    echo "Build and Push Successfully"
                }
                failure{
                    echo "Build and Push Failed"
                }
            }
        }
stage('Image Scan') {
            steps {
                //Put your image scanning tool 
                echo 'Image Scanning Start'
            }
            post{
                success{
                    echo "Image Scanning Successfully"
                }
                failure{
                    echo "Image Scanning Failed"
                }
            }
        }
stage("Deploy to Production"){
            when {
                branch 'master'
            }
            steps { 
                kubernetesDeploy kubeconfigId: 'kubeconfig-credentials-id', configs: 'YOUR_YAML_PATH/your_k8s_yaml', enableConfigSubstitution: true  // REPLACE kubeconfigId
 
             }
            post{
                success{
                    echo "Successfully deployed to Production"
                }
                failure{
                    echo "Failed deploying to Production"
                }
            }
        }
stage("Deploy to Staging"){
            when {
                branch 'staging'
            }
            steps {
                kubernetesDeploy kubeconfigId: 'kubeconfig-credentials-id', configs: 'YOUR_YAML_PATH/your_k8s_yaml', enableConfigSubstitution: true  // REPLACE kubeconfigId
             }
            post{
                success{
                    echo "Successfully deployed to Staging"
                }
                failure{
                    echo "Failed deploying to Staging"
                }
            }
        }
}
 
    post{
        always{
step([
             //put your Testing
            ])
        }
        success{
            //notification webhook
            echo 'Pipeline Execution Successfully Notification'
}
        failure{
            //notification webhook
            echo 'Pipeline Execution Failed Notification'
}
    }
}
