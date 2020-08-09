# k8s-telepresence-demo
The purpose of this demo is to showcase how can we **debug a Kubernetes service locally using Telepresence**.
There is a minimal client-server **Kubernetes application**, with a **React (Javascript)** front-end, and a **SpringBoot (Java)** back-end.
The application can be packaged in a **Helm chart**.

### Pre-requisites
You need to have [Docker](https://docs.docker.com), [Kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/), [Minikube](https://github.com/kubernetes/minikube), [NGINX Ingress Controller](https://docs.nginx.com/nginx-ingress-controller/installation/installation-with-helm/), [Helm](https://github.com/helm/helm), and [Telepresence](https://www.telepresence.io/reference/install) installed.

### Installation
- To **build** the demo (build docker images and package the helm chart) : ```.\gradlew buildApp```
- To **build and deploy** into a Kubernetes cluster: ```.\gradlew deployApp```

### Run the server locally
- After deploying the application to the Kubernetes cluster: 
    - Go to the server folder: ```$ cd server```
    - Build a local docker image: ```$ docker build -t k8s-demo-server-img .```
    - **Swap** the **remote deployment** with the **local Docker image**: ```$ telepresence --swap-deployment k8s-demo:server --expose 8000:8002 --docker-run --rm -it -v $(pwd):/usr/src/app k8s-demo-server-img```
    - If you want to **remote debug** the back-end: ```$ telepresence --swap-deployment k8s-demo:server --expose 8000:8002 --docker-run --rm -it -p 5005:5005 -e JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=y -v $(pwd):/usr/src/app k8s-demo-server-img```
    - In case you are **using a Kubernetes client**: ```telepresence --mount /tmp/known --swap-deployment k8s-demo:server --expose 8000:8002 --docker-run --rm -it -v $(pwd):/usr/src/app -v=/tmp/known/var/run/secrets:/var/run/secrets k8s-demo-server-img```
    - **All in one!**: ```telepresence --mount /tmp/known --swap-deployment k8s-demo:server --expose 8000:8002 --docker-run --rm -it -p 5005:5005 -e JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=y -v $(pwd):/usr/src/app -v=/tmp/known/var/run/secrets:/var/run/secrets k8s-demo-server-img```
### Project Structure
<pre>
|-- client/
|   |-- src/: front-end source code.
|   |-- <b>Dockerfile: front-end Docker image recipe.</b>
|   |-- build.gradle: build Docker image task.
|   |-- package.json: project metadata.
|-- server/
|   |-- build/: build directory (jar file destination).
|   |-- src/: back-end source code.
|   |-- <b>Dockerfile: back-end Docker image recipe.</b>
|   |-- build.gradle: build Docker image task.
|-- helm/
|   |-- chart/
|   |   |-- <b>templates/: parameterizable Kubernetes objects.</b>
|   |   |-- Chart.yaml: name and version information.
|   |   |-- <b>values.yaml: values to feed the templates.</b>
|   |-- build.gradle: helm chart package task.
|-- build.gradle: build and deploy tasks.
|-- settings.gradle
</pre>

### Kubernetes Architecture
- Client and Server are running in different Docker **containers**, inside a single **pod**.
- A **service** is exposing the **pod** inside the Kubernetes cluster.
- The **ingress** allows access to the **service** from outside the Kubernetes cluster.

![K8s Demo Architecture](https://github.com/hv-leo/k8s-telepresence-demo/blob/master/docs/k8s-demo-arch.png "K8s Demo Architecture")

### Demo
To access the application: 
- **Front-end**: ```http://<hostname>/client```
- **Backend**: 
    - ```http://<hostname>/server```
    - ```http://<hostname>/server/services```

![K8s Demo Web App](https://github.com/hv-leo/k8s-telepresence-demo/blob/master/docs/k8s-demo-app.png "K8s Demo Web App")

## Authors:
- Leonardo Coelho	- <leonardo.coelho@ua.pt>
