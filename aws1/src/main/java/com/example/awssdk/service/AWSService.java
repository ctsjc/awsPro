package com.example.awssdk.service;

import com.example.awssdk.AWSRequestDTO;
import com.example.awssdk.AWSResponseDTO;
import com.example.awssdk.awsl.AmazonIdentityManagementService;
import com.example.awssdk.awsl.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AWSService {
    @Autowired
    S3Service s3Service;

    @Autowired
    AmazonIdentityManagementService amazonIdentityManagementService;

    public List<String> listBucketNames(){
        return s3Service.listBucketNames();
    }

    public AWSResponseDTO createBucket(String bucketName) {
        AWSResponseDTO resp = s3Service.createBucket(bucketName);
        log.info("Response for create {}",resp);
        return resp;
    }

    public AWSResponseDTO deleteBucket(String bucketName) {
        AWSResponseDTO resp = s3Service.deleteBucket(bucketName);
        log.info("Response for deleteBucket {}",resp);
        return resp;
    }

    public AWSResponseDTO assumeRole(AWSRequestDTO awsRequestDTO) {
        AWSResponseDTO resp = s3Service.assumeRole(awsRequestDTO);
        log.info("Response for deleteBucket {}",resp);
        return resp;
    }

    public void readPolicies(){
        amazonIdentityManagementService.readPolicies();
    }

    public void assumeRole1(){
        amazonIdentityManagementService.assumeRole();
    }


    public void attachUserPolicy(){
        log.info("attaching UserPolicy");
        amazonIdentityManagementService.attachUserPolicy();
    }


    public void createUserPolicyInline(){
        log.info("attaching UserPolicy");
        amazonIdentityManagementService.createUserPolicyInline();
    }
}
