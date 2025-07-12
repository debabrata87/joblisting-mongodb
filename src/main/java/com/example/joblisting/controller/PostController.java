package com.example.joblisting.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.joblisting.model.Post;
import com.example.joblisting.repo.PostRepository;
import com.example.joblisting.repo.Search;

import javax.servlet.http.HttpServletResponse;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class PostController {
	
	@Autowired
	PostRepository repo;
	@Autowired
	Search repo2;
	
	@ApiIgnore
	@RequestMapping(value="/")
	public static void redirect(HttpServletResponse res) throws IOException {
		res.sendRedirect("/swagger-ui.html");
	}

	
	@GetMapping(value="/seejobpost")
	public List<Post> getAllPost(){
		return repo.findAll();
	}
	
	
	@GetMapping(value="/filterjobpost/{searchtext}")
	public List<Post> filterJobPost(@PathVariable String searchtext){
		return repo2.searchJobPost(searchtext);
	}
	
	@PostMapping(value="/addjobpost")
	public Post addPost(@RequestBody Post post){
		return repo.save(post);
	}
	
	
}
