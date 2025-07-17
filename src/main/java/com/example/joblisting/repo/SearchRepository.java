package com.example.joblisting.repo;

import java.util.List;

import com.example.joblisting.model.Post;

public interface SearchRepository {

	 public List<Post> searchJobPost(String text);
}
