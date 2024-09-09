package com.base.servicer1.services.interfaces;

import com.base.servicer1.entities.SQSMessage;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

public interface ISQSService {
    List<String> listQueues();

    String createQueue(String name);

    void deleteQueue(String queueName);

    String sendMessage(String queueName, String message);

    List<SQSMessage> receiveMessages(String queueUrl);

    void deleteMessages(String queueUrl, List<Message> messages);
}
