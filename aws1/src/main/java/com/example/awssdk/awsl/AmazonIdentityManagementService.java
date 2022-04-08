package com.example.awssdk.awsl;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagement;
import com.amazonaws.services.identitymanagement.model.AttachRolePolicyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AmazonIdentityManagementService {
  //  @Autowired
    AmazonIdentityManagement amazonIdentityManagement;

    void m() {
        AttachRolePolicyRequest attachRolePolicyRequest = new AttachRolePolicyRequest();
        amazonIdentityManagement.attachRolePolicy(attachRolePolicyRequest);
    }
}
