package com.example.joblisting.service;

import org.springframework.stereotype.Component;

import com.example.joblisting.utility.Utility;


@Component
public class UtilityService {

	public String processMessage() {
		return Utility.getWelcomeMessageStatic().toUpperCase();
	}

}
