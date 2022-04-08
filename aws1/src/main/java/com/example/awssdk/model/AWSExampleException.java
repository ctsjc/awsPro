package com.example.awssdk.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AWSExampleException extends RuntimeException{
    public AWSExampleException() {
    }

    public AWSExampleException(String message) {
        super(message);
    }

    public AWSExampleException(String message, Throwable cause) {
        super(message, cause);
    }

    public AWSExampleException(Throwable cause) {
        super(cause);
    }

    public AWSExampleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
