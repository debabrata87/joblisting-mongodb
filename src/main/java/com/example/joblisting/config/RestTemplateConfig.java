package com.example.joblisting.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	@Value("${external.service.timeout.connection}")
	private int connectionTimeout;

    @Bean
    public RestTemplate restTemplate() {
        
        
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(connectionTimeout).setConnectTimeout(connectionTimeout).build())
                .build();
         return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }
}