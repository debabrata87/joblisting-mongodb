package com.example.joblisting.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.joblisting.model.Post;

public interface PostRepository extends MongoRepository<Post, String>{

}
