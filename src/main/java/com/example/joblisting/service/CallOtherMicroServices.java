package com.example.joblisting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CallOtherMicroServices {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private WebClient.Builder webClientBuilder;

	private final ExternalServiceClient externalServiceClient;

	@Autowired
	public CallOtherMicroServices(ExternalServiceClient externalServiceClient) {
		this.externalServiceClient = externalServiceClient;
	}

	

	@CircuitBreaker(name = "externalService", fallbackMethod = "fallbackMethodV1")
	public String callExternalServiceV1(String name) {
		String url = "http://localhost:8085/greetv1/" + name; // Replace with actual URL
		try {
			return restTemplate.getForObject(url, String.class); // Make a call to another service

		} catch (Exception e) {
			// Log the exception for debugging purposes
			e.printStackTrace(); // Or use a logger: log.error("Error calling external service", e);

			// Rethrow the exception so the circuit breaker can be triggered
			throw new RuntimeException("Error calling external service", e);
		}
	}

	// Fallback method to be used if the circuit breaker is open
	public String fallbackMethodV1(Exception ex) {

		System.out.println("Fallback method triggered: " + ex.getMessage());
		ex.printStackTrace(); // Log the stack trace
		return "Service Service DevOpsDemo greet endpoint is temporarily unavailable Using RestTemplate. Please try again later!";
	}

	@CircuitBreaker(name = "externalService", fallbackMethod = "fallbackMethodV2")
	public String callExternalServiceV2(String name) {
		return webClientBuilder.baseUrl("http://localhost:8085").build().get().uri("/greetv2/" + name).retrieve()
				.bodyToMono(String.class).block();
	}

	// Fallback method to be used if the circuit breaker is open
	public String fallbackMethodV2(String name, Throwable t) {
		return "Service DevOpsDemo greetv2 endpoint is temporarily unavailable Using WebClient. Please try again later!";
	}

	
	@CircuitBreaker(name = "externalService", fallbackMethod = "fallbackMethodV3")
	public String callExternalServiceV3(String name) {

		try {
			return "From Feign : " + externalServiceClient.greetV1(name);

		} catch (Exception e) {
			// Log the exception for debugging purposes
			e.printStackTrace(); // Or use a logger: log.error("Error calling external service", e);

			// Rethrow the exception so the circuit breaker can be triggered
			throw new RuntimeException("Error calling external service via Feign", e);
		}
	}

	public String fallbackMethodV3(Exception ex) {

		System.out.println("Fallback method triggered for Fiegn: " + ex.getMessage());
		ex.printStackTrace(); // Log the stack trace
		return "Service Service DevOpsDemo greet endpoint is temporarily unavailable Using Feign!. Please try again later!";
	}
	
	
	@Retryable(value = {
			ResourceAccessException.class }, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 2))
	public String callExternalServiceV4(String name) {
		
		System.out.println("Triggering  callExternalServiceV4 \n");
		String url = "http://localhost:8085/greetv1/" + name;
		return restTemplate.getForObject(url, String.class);
	}

	// This method will be called when retries are exhausted
	@Recover
	public String fallbackMethodBackoffRetry(ResourceAccessException ex, String name) {
		return "Service DevOpsDemo greetv1 endpoint is temporarily unavailable using Fallback Retry . Please try again later!" ;
	}

}
