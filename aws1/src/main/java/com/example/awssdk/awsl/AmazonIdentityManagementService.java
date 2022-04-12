package com.example.awssdk.awsl;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AmazonIdentityManagementService {
    public static final String POLICY_ARN =
            "arn:aws:iam::aws:policy/AmazonS3FullAccess";
    @Autowired
    AmazonIdentityManagement amazonIdentityManagement;

    void m() {
        AttachRolePolicyRequest attachRolePolicyRequest = new AttachRolePolicyRequest();
        amazonIdentityManagement.attachRolePolicy(attachRolePolicyRequest);
    }


    public void attachUserPolicy(){
        log.info("attachUserPolicy");
        ListAttachedUserPoliciesRequest userPolicies=new ListAttachedUserPoliciesRequest().withUserName("sdkUser");
        ListAttachedUserPoliciesResult attachedUserPolicies
                = amazonIdentityManagement.listAttachedUserPolicies(userPolicies);
        attachedUserPolicies.getAttachedPolicies().forEach(attachedPolicy -> {
            log.info("attachedPolicy.getPolicyArn() :: {}",attachedPolicy.getPolicyArn());
        });

        AttachUserPolicyRequest attach_request =
                new AttachUserPolicyRequest()
                        .withUserName("sdkUser")
                        .withPolicyArn(POLICY_ARN);
        AttachUserPolicyResult attachResult = amazonIdentityManagement.attachUserPolicy(attach_request);


        log.info("attachResult {}" , attachResult);
    }

    public void createUserPolicyInline(){
        String inlineJson ="{\"Version\": \"2012-10-17\", \"Statement\": [{\"Sid\": \"VisualEditor0\", \"Effect\": \"Allow\", \"Action\": \"s3:ListBucket\", \"Resource\": \"*\"}]}";
        ListAttachedUserPoliciesRequest userPolicies=new ListAttachedUserPoliciesRequest().withUserName("sdkUser");

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

    }
}
