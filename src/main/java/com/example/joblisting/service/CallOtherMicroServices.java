package com.example.joblisting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;



@Service
public class CallOtherMicroServices {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	
	@CircuitBreaker(name = "externalService", fallbackMethod = "fallbackMethodV1" )
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
		return "Service Service DevOpsDemo greet endpoint is temporarily unavailable. Please try again later!";
	}

	@CircuitBreaker(name = "externalService", fallbackMethod = "fallbackMethodV2")
	public String callExternalServiceV2(String name) {
		return webClientBuilder.baseUrl("http://localhost:8085").build().get().uri("/greetv2/" + name).retrieve()
				.bodyToMono(String.class).block();
	}

	// Fallback method to be used if the circuit breaker is open
	public String fallbackMethodV2(String name, Throwable t) {
		return "Service DevOpsDemo greetv2 endpoint is temporarily unavailable. Please try again later!";
	}

}
