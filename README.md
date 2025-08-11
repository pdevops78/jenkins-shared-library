# jenkins-shared-library


global pipeline libraries
name: expense

webhooks:
========
multi branch scan webhook trigger

JENKINS_URL/multibranch-webhook-trigger/invoke?token=[Trigger token]

http://54.163.83.145:8080/multibranch-webhook-trigger/invoke?token=expense-backend



eks:
----
pod is a list of containers
ex:
---
apiVersion: v1
kind: Pod
metadata:
   name: pod-01
spec:
   containers: 
      - name: nginx
        image: nginx
      - name: tomcat
        image: tomcat

kubectl apiVersion
to know pod in yaml : kubectl get pods pod-01 -o yaml

two advantages in pod:
1. network 
2. storage: volumeMounts is the storage which is used to store the data/content.

"How do I get inside a container running in a specific pod?"

Or

"How do I exec into a container in a specific pod?"

kubectl exec -it pod-01 -c nginx -- bash

ex:
--
FROM  centos
COPY  run.sh /
ENTRYPOINT ["bash","/run.sh"]

run.sh
------
mkdir -p /usr/share/nginx/html
while true ; do
echo "<h1>Hello-$(date)</h1>" >/usr/share/nginx/html/index.html
sleep 1
done

curl localhost

create an eks cluster
create a node group
create a launch template to create an ec2 


steps to create an eks cluster:
===============================
* cluster name
* iam role
* create vpc
* create subnets
* create security group
* add kube-proxy,core-dns,node monitoring agent, amazon vpc cni


1. create a cluster

