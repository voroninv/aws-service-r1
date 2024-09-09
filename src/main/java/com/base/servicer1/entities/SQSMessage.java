package com.base.servicer1.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SQSMessage {
    private String messageId;
    private String body;
    private String receiptHandle;
}
