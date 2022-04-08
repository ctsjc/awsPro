package com.example.awssdk.service;

import com.example.awssdk.AWSRequestDTO;
import com.example.awssdk.AWSResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@Slf4j
class AWSServiceTest {

    @Autowired
    AWSService awsService;

    @Test
    @Order(1)
    void createBucket() {
        AWSResponseDTO resp = awsService.createBucket("nenjukkulpeidhidum");
        log.info("Resp :: {}",resp);
    }

    @Test
    @Order(2)
    void listBucketNames() {
        awsService.listBucketNames().forEach(System.out::println);
    }


    @Test
    @Order(3)
    void deleteBucket() {
        AWSResponseDTO resp = awsService.deleteBucket("nenjukkulpeidhidum");
        log.info("Resp :: {}",resp);
    }

    @Test
    void assumeRole() {
        AWSRequestDTO awsRequestDTO=new AWSRequestDTO();
        awsService.assumeRole( awsRequestDTO );
    }
}