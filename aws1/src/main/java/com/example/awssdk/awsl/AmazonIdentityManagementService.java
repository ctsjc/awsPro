package com.example.awssdk.awsl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.Credentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AmazonIdentityManagementService {
    public static final String POLICY_ARN =
            "arn:aws:iam::aws:policy/AmazonS3FullAccess";
    @Autowired
    AmazonIdentityManagement amazonIdentityManagement;


    @Autowired
    AWSSecurityTokenService stsClient;

    @Value("${roleArn}")
    String roleArn="arn:aws:iam::976784444277:role/sdk-role";

    @Value("${roleSessionName}")
    String roleSessionName="CloudWatch_Session";


    @Value("${userName}")
    String userName;

    void m() {
        AttachRolePolicyRequest attachRolePolicyRequest = new AttachRolePolicyRequest();
        amazonIdentityManagement.attachRolePolicy(attachRolePolicyRequest);
    }


    public void attachUserPolicy(){
        log.info("attachUserPolicy");
        ListAttachedUserPoliciesRequest userPolicies=new ListAttachedUserPoliciesRequest().withUserName(userName);
        ListAttachedUserPoliciesResult attachedUserPolicies
                = amazonIdentityManagement.listAttachedUserPolicies(userPolicies);
        attachedUserPolicies.getAttachedPolicies().forEach(attachedPolicy -> {
            log.info("attachedPolicy.getPolicyArn() :: {}",attachedPolicy.getPolicyArn());
        });

        AttachUserPolicyRequest attach_request =
                new AttachUserPolicyRequest()
                        .withUserName(userName)
                        .withPolicyArn(POLICY_ARN);
        AttachUserPolicyResult attachResult = amazonIdentityManagement.attachUserPolicy(attach_request);


        log.info("attachResult {}" , attachResult);
    }

    public void createUserPolicyInline(){
        String inlineJson ="{\"Version\": \"2012-10-17\", \"Statement\": [{\"Sid\": \"VisualEditor0\", \"Effect\": \"Allow\", \"Action\": \"s3:ListBucket\", \"Resource\": \"*\"}]}";
        ListAttachedUserPoliciesRequest userPolicies=new ListAttachedUserPoliciesRequest().withUserName(userName);

        ListAttachedUserPoliciesResult attachedUserPolicies
                = amazonIdentityManagement.listAttachedUserPolicies(userPolicies);
        attachedUserPolicies.getAttachedPolicies().forEach(attachedPolicy -> {
            log.info("attachedPolicy.getPolicyArn() :: {}",attachedPolicy.getPolicyArn());
        });

        CreatePolicyRequest createPolicyRequest=new CreatePolicyRequest().withPolicyDocument(inlineJson).withPolicyName("inline-s3-read-policy");
        CreatePolicyResult result = amazonIdentityManagement.createPolicy(createPolicyRequest);
        log.info("Result :: {}", result);


    }
    public  void readPolicies() {
        ListPoliciesResult listPoliciesResult = amazonIdentityManagement.listPolicies();
        List<Policy> policyList = listPoliciesResult.getPolicies();
        policyList.forEach(policy -> {
            log.info("ARN {}", policy.getArn());
            log.info("isAttachable {}", policy.isAttachable());
        });
    }

    public void assumeRole(){

        AssumeRoleRequest assumeRoleRequest = new AssumeRoleRequest().
                withRoleArn(roleArn).withRoleSessionName(roleSessionName);
        AssumeRoleResult assumeRoleResult = stsClient.assumeRole(assumeRoleRequest);
        Credentials sessionCredentials = assumeRoleResult.getCredentials();
        BasicSessionCredentials basicSessionCredentials=new BasicSessionCredentials(
                sessionCredentials.getAccessKeyId(),
                sessionCredentials.getSecretAccessKey(),
                sessionCredentials.getSessionToken()
        );

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(basicSessionCredentials)).build();

        List<Bucket> resp = s3Client.listBuckets();
        log.info("Response for Bucket is {}", resp);
    }
}
