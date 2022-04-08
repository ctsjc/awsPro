package com.example.awssdk.awsl;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeAddressesResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EC2Service {
  //  @Autowired
    AmazonEC2 ec2;
    void method1(){
        DescribeAddressesResult address = ec2.describeAddresses();
        List<String> domains=address.getAddresses().stream().map(address1 -> address1.getDomain()).collect(Collectors.toList());
        log.info("Domains:: {}", domains);
    }
}
