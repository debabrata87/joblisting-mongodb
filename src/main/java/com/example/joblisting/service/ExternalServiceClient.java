package com.example.joblisting.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "devops-demo")
public interface ExternalServiceClient {

    @GetMapping("/greetv1/{name}")
    String greetV1(@PathVariable("name") String name);
}