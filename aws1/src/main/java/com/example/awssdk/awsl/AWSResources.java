package com.example.awssdk.awsl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class AWSResources {
    @Value("${accessKey}")
    String accessKey;
    @Value("${secretKey}")
    String secretKey;

    @Bean
    AWSSecurityTokenService stsClient(){
        AWSSecurityTokenService securityTokenService= AWSSecurityTokenServiceClientBuilder.standard().
                withCredentials(new AWSStaticCredentialsProvider(getAwsCredentials())).build();
        return securityTokenService;
    }

    @Bean
    AmazonIdentityManagement amazonIdentityManagement(){
        AmazonIdentityManagement creds = AmazonIdentityManagementClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(getAwsCredentials())).build();
        return creds;
    }
    @Bean
    AmazonEC2 ec2(){
        AWSCredentials awsCredentials = getAwsCredentials();

        return AmazonEC2ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }

    @Bean
    public AmazonS3 amazonS3(){
        AWSCredentials awsCredentials = getAwsCredentials();
        Region region = Region.US_West_2;

        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
    }

    private AWSCredentials getAwsCredentials() {
        log.info("accessKey {}",accessKey);
        AWSCredentials awsCredentials =new BasicAWSCredentials(accessKey, secretKey);
        return awsCredentials;
    }
}
