package com.example.awssdk.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class AWSIdentityMgmtTest {
    @Autowired
    AWSService awsService;

    @Test
    void attachUserPolicy() {
        awsService.attachUserPolicy();
    }

    @Test
    void createUserPolicyInline() {
        awsService.createUserPolicyInline();
    }
}
