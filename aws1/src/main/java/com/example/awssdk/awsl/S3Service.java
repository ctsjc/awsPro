package com.example.awssdk.awsl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.example.awssdk.AWSRequestDTO;
import com.example.awssdk.AWSResponseDTO;
import com.example.awssdk.model.AWSExampleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class S3Service {
    @Autowired
    AmazonS3 amazonS3;

    public List<String> listBucketNames(){

        List<Bucket> buckets = amazonS3.listBuckets();
        List<String> bucketNames = buckets.stream().map(bucket -> bucket.getName()).collect(Collectors.toList());
        log.info("Bucket Names {}",bucketNames);
        return bucketNames;
    }

    public AWSResponseDTO createBucket(String bucketName) {
        log.info("Creating bucket {}", bucketName);
        Bucket bucket = amazonS3.createBucket(bucketName);
        log.info("AWS Raw bucket Response | {}", bucket);
        AWSResponseDTO awsResponseDTO = new AWSResponseDTO();
        awsResponseDTO.setName(bucket.getName());
        awsResponseDTO.setTimestamp( bucket.getCreationDate());
        awsResponseDTO.setStatus("OK");
        return awsResponseDTO;
    }

    public AWSResponseDTO deleteBucket(String bucketName){
        log.info("Deleting bucket {}", bucketName);
        AWSResponseDTO awsResponseDTO = new AWSResponseDTO();
        amazonS3.deleteBucket(bucketName);
        return awsResponseDTO;
    }


    public AWSResponseDTO assumeRole(AWSRequestDTO awsRequestDTO) {
        throw new AWSExampleException("assumeRole is not Implemented.");
    }
}
