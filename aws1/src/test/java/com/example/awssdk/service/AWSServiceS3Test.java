package com.example.awssdk.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AWSServiceS3Test {

    @Autowired
    AWSService awsService;
/*

    @Test
    @Order(1)
    void createBucket() {
        AWSResponseDTO resp = awsService.createBucket("nenjukkulpeidhidum");
        log.info("Resp :: {}",resp);
    }
*/

    @Test
    @Order(2)
    void listBucketNames() {
        // Get Access
        awsService.attachUserPolicy();
        awsService.listBucketNames().forEach(bucket ->{
            log.info("Bucket {}", bucket);
        });
    }

/*
    @Test
    @Order(3)
    void deleteBucket() {
        AWSResponseDTO resp = awsService.deleteBucket("nenjukkulpeidhidum");
        log.info("Resp :: {}",resp);
    }*/

}