package com.base.servicer1.services;

import com.base.servicer1.entities.SQSMessage;
import com.base.servicer1.services.interfaces.ISQSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SQSService implements ISQSService {

    private static final Logger logger = LogManager.getLogger(S3Service.class);

    @Autowired
    SqsClient sqsClient;

    public List<String> listQueues() {
        ListQueuesResponse listQueuesResponse = sqsClient.listQueues();

        return listQueuesResponse.queueUrls();
    }

    public String createQueue(String name) {
        Map<QueueAttributeName, String> queueAttributes = new HashMap<>();
        queueAttributes.put(QueueAttributeName.FIFO_QUEUE, "true");
        queueAttributes.put(QueueAttributeName.CONTENT_BASED_DEDUPLICATION, "true");

        CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
                .queueName(name)
                .attributes(queueAttributes)
                .build();

        CreateQueueResponse createQueueResponse = sqsClient.createQueue(createQueueRequest);
        String queueUrl = createQueueResponse.queueUrl();

        logger.info(String.format("r1: sqs queue created. url: %s .", queueUrl));

        return queueUrl;
    }

    public void deleteQueue(String queueName) {
        sqsClient.deleteQueue(
                DeleteQueueRequest.builder()
                        .queueUrl(getQueueUrl(queueName))
                        .build()
        );
    }

    public String sendMessage(String queueName, String message) {
        SendMessageResponse sendMessageResponse = sqsClient.sendMessage(
                SendMessageRequest.builder()
                        .queueUrl(getQueueUrl(queueName))
                        .messageBody(message)
                        .messageGroupId(queueName + "_group_1")
                        .build()
        );

        logger.info(String.format("r1: sqs: message sent to: %s.", queueName));

        return sendMessageResponse.messageId();
    }

    public List<SQSMessage> receiveMessages(String queueName) {
        String queueUrl = getQueueUrl(queueName);

        List<Message> messages = sqsClient.receiveMessage(
                ReceiveMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(5)
                        .build()
        ).messages();

        List<SQSMessage> sqsMessages = toSQSMessages(messages);
        deleteMessages(queueUrl, messages);

        logger.info(String.format("r1: sqs: messages received from: %s.", queueName));

        return sqsMessages;
    }

    public void deleteMessages(String queueUrl, List<Message> messages) {
        messages.stream()
                .map(msg -> DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(msg.receiptHandle())
                        .build()
                ).forEach(request -> sqsClient.deleteMessage(request));
    }

    private List<SQSMessage> toSQSMessages(List<Message> messages) {
        return messages.stream()
                .map(msg -> SQSMessage.builder()
                        .messageId(msg.messageId())
                        .body(msg.body())
                        .receiptHandle(msg.receiptHandle())
                        .build()
                )
                .toList();
    }

    private String getQueueUrl(String queueName) {
        return sqsClient.getQueueUrl(GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build()).queueUrl();
    }
}
