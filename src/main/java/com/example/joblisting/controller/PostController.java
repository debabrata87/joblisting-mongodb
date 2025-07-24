package com.example.joblisting.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.joblisting.model.Post;
import com.example.joblisting.repo.PostRepository;
import com.example.joblisting.repo.SearchRepository;
import com.example.joblisting.service.CallOtherMicroServices;

import com.example.joblisting.service.UserService;
import com.example.joblisting.service.UtilityService;
//import jakarta.servlet.http.HttpServletResponse;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/joblisting")
public class PostController {

	@Autowired
	PostRepository repo;
	@Autowired
	SearchRepository repo2;

	@Autowired
	UtilityService utilService;

	@Autowired
	private UserService userService;

	@Autowired
	private CallOtherMicroServices otherMicroService;

	@ApiIgnore
	@RequestMapping(value = "/")
	public static void redirect(HttpServletResponse res) throws IOException {
		res.sendRedirect("/swagger-ui.html");
	}

	@GetMapping("/welcome/{name}")
	public String greet(@PathVariable String name) {
		// Invoke-RestMethod -Uri "http://localhost:8086/joblisting/welcome/Debabrata" -Method Get
		return "App Name : Job Listing ,  User Name : " + name + "!";
	}

	@GetMapping("/welcomev2/{name}")
	public String callAnotherServiceV1(@PathVariable("name") String name) {

		return otherMicroService.callExternalServiceV1(name);

	}

	@GetMapping("/welcomev3/{name}")
	public String callAnotherServiceV2(@PathVariable("name") String name) {
		return otherMicroService.callExternalServiceV2(name);
	}

	@GetMapping("/welcomev4/{name}")
	public String callAnotherServiceV3(@PathVariable("name") String name) {
		// This is with Feign Client
		return otherMicroService.callExternalServiceV3(name);

	}

	@GetMapping("/welcomev5/{name}")
	public String callAnotherServiceV4(@PathVariable("name") String name) {
		return otherMicroService.callExternalServiceV4(name);
	}

	@GetMapping(value = "/seejobpost")
	public List<Post> getAllPost() {
		return repo.findAll();
	}

	@GetMapping(value = "/filterjobpost/{searchtext}")
	public List<Post> filterJobPost(@PathVariable String searchtext) {

		// Invoke-RestMethod -Uri "http://localhost:8086/joblisting/filterjobpost/kolkata" -Method Get
		
		return repo2.searchJobPost(searchtext);
	}

	@PostMapping(value = "/addjobpost")
	public Post addPost(@RequestBody Post post) {

		/*
		 * powershell request $body = @{ profile = "Block Chain Developer" desc =
		 * "Kolkata Block Chain Developer Test 1" exp = 10 techs = @("java", "xml",
		 * "spring mvc", "banking payment","plsql","data analytics") } | ConvertTo-Json
		 * -Depth 3
		 * 
		 * Invoke-RestMethod -Uri "http://localhost:8086/joblisting/addjobpost" ` -Method Post `
		 * -Body $body ` -ContentType "application/json"
		 */

		return repo.save(post);
	}

	public String processMessage() {
		return utilService.processMessage();
	}

	@PutMapping("/updatejobpostexp")
	public String updateStatus(@RequestParam String profile, @RequestParam String desc, @RequestParam int exp) {

		// Invoke-RestMethod -Uri "http://localhost:8086/joblisting/updatejobpostexp?profile=Block
		// Chain Developer &desc=Kolkata Block Chain Developer Test 1&exp=10" -Method
		// Put

		boolean updated = userService.updateUserExp(profile, desc, exp);
		return updated ? "User updated successfully" : "User not found";
	}

	@DeleteMapping("/deletejobpostexp")
	public String deleteJobPost(@RequestParam String profile, @RequestParam String desc) {

		// Invoke-RestMethod -Uri "http://localhost:8086/joblisting/deletejobpostexp?profile=Block
		// Chain Developer&desc=Kolkata Block Chain Developer Test 1" -Method Delete

		long deleted = userService.deleteUserByProfileAndDex(profile, desc);

		return deleted > 0 ? "User deleted successfully." : "No user found.";

	}

}
