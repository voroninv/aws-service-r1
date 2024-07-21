package com.base.servicer1.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

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
        return AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
                .build();
    }

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials()))
                .withRegion(region)
                .build();
    }

    @Bean
    SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.EU_NORTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials()))
                .build();
    }

    @Bean
    AWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKeyId, secretAccessKey);
    }

    @Bean
    AwsBasicCredentials awsBasicCredentials() {
        return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
    }
}
