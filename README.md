## aws-service-r1
AWS SDK showcase

<br/>

## Description

<br/>

This service aims to demonstrate the capabilities of the AWS SDK, which offers a Java API for AWS services.
It shows how easily  you can build Java applications that work with Amazon S3, Lambda, Amazon EC2, DynamoDB and other services.

<br/>

## Endpoints
- DynamoController:  create, delete, check table description and get a list of available tables in Dynamo DB;
- DynamoOrderController: get, add, delete order or get order list;
- LambdaController: create, invoke, delete and list available functions;
- S3Controller: create, delete or list available s3 buckets;
- S3ImageController: upload, download, delete or list image files.

<br/>

## GitHub action
There are also two **GitHub** workflows configured for CI/CD automation:
- develop configuration is for running tests and packaging an application.
- master configuration contains steps for testing, building and deploying service to Amazon Elastic Beanstalk. 

<br/>

## Prerequisites
- AWS Account: You should have an Amazon Web Services account to be able to work with AWS SDK.
- AWS Security Credentials: access keys that allow us to make programmatic calls to AWS API actions. You can use either AWS root account credentials or IAM user credentials.
- AWS Region: you have to select the AWS region.

<br/>

## Libraries
- aws java sdk lambda;
- aws java sdk dynamodb;
- aws java sdk s3;
- derjust spring data dynamodb;
- apache commons lang;
- log4j;
- lombok.