#!/bin/bash

# Variables
export DOCKER_IMAGE_NAME="myslave"
export DOCKER_IMAGE_TAG="1.0.0"
export DOCKERFILE_PATH="./Dockerfile.native"
export K8S_DEPLOYMENT_FILE="deployment.yaml"
export K8S_SERVICE_FILE="service.yaml"
export K8S_INGRESS_FILE="ingress.yaml"
export K8S_NAMESPACE="development"
export K8S_HPA_FILE="hpa.yaml"

# Step 1: Build the Docker image only locally
echo "Building Docker image..."
docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} -f ${DOCKERFILE_PATH} .

cd "$(dirname "$0")"/k8s

# Step 2: Apply the Kubernetes namespace (if not already created)
echo "Creating Kubernetes namespace..."
kubectl create namespace ${K8S_NAMESPACE} || true

# Step 3: Apply the Kubernetes Deployment - In this step we get variables from the environment and substitute them in the deployment.yaml file
# The envsubst command is used to substitute the variables in the deployment.yaml file with the values from the environment
# Adicionaly, we nedd to install the envsubst command $ sudo apt-get install gettext-base
echo "Applying Kubernetes Deployment..."
envsubst < ${K8S_DEPLOYMENT_FILE} | kubectl apply -f -

# Step 4: Apply the Kubernetes HPA
echo "Waiting for the Hpa to be ready..."
kubectl apply -f ${K8S_HPA_FILE}

# Step 5: Apply the Kubernetes Service
echo "Applying Kubernetes Service..."
kubectl apply -f ${K8S_SERVICE_FILE}

# Step 6: Apply the Kubernetes Ingress
echo "Applying Kubernetes Ingress..."
kubectl apply -f ${K8S_INGRESS_FILE}

echo "Deployment complete."