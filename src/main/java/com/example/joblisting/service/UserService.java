package com.example.joblisting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import com.example.joblisting.model.Post;

@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean updateUserExp(String profile, String desc, int newExp) {
        Query query = new Query();
        query.addCriteria(Criteria.where("profile").is(profile));
        query.addCriteria(Criteria.where("desc").is(desc));

        Update update = new Update();
        update.set("exp", newExp);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Post.class);

        return result.getModifiedCount() > 0;
    }
    
    public long deleteUserByProfileAndDex(String profile, String desc) {
        Query query = new Query();
        query.addCriteria(Criteria.where("profile").is(profile));
        query.addCriteria(Criteria.where("desc").is(desc));

        // Delete all matching documents
        DeleteResult result = mongoTemplate.remove(query, Post.class);

        return result.getDeletedCount(); // returns number of documents deleted
    }
    
}