package com.example.joblisting.repo;

import java.util.ArrayList;
import java.util.List;



import com.example.joblisting.model.Post;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import jdk.internal.org.jline.terminal.TerminalBuilder.SystemOutput;

import java.util.Arrays;

import org.bson.Document;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Component;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;

@Component
public class SearchImpl implements SearchRepository{
	
	@Autowired
	MongoClient mongoClient ;
	@Autowired
	MongoConverter conv;
	
	@Value("${spring.data.mongodb.database}")
    private String dbName;

	@Value("${job.collection.name}")
    private String jobCollectionName;
	
	
	
	@Override
	public List<Post> searchJobPost(String searchText) {
		
		
		 	ArrayList<Post> listPost= new ArrayList<Post>();
			MongoDatabase database = mongoClient.getDatabase(dbName);
			MongoCollection<Document> collection = database.getCollection(jobCollectionName);
			AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
			    new Document("index", "default-jobpost-search")
			            .append("text", 
			    new Document("query",  searchText)
			                .append("path", Arrays.asList("desc", "techs")))), 
			    new Document("$sort", 
			    new Document("exp", 1L))));
			
			result.forEach( doc -> listPost.add( conv.read(Post.class, doc)));

		    listPost.stream().forEach((Post p)-> System.out.print( p.toString()) );
			return listPost;
	}

}
