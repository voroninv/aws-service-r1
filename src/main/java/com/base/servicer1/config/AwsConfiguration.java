package com.base.servicer1.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDynamoDBRepositories(basePackages = "com.base.servicer1.repositories")
public class AwsConfiguration {

    @Value("${aws.auth.accessKeyId}")
    private String accessKeyId;

    @Value("${aws.auth.secretAccessKey}")
    private String secretAccessKey;

    @Value("${aws.auth.region}")
    private String region;

    @Bean
    AWSLambda awsLambda() {
        return AWSLambdaClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
                .withRegion(region).build();
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
                .build();
    }

    @Bean
    AWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKeyId, secretAccessKey);
    }
}
