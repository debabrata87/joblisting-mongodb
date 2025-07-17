package com.example.joblisting.model;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "JobPost")
public class Post {
    private String profile;
    private String desc;
    private int exp;
    private String techs[];

    public Post() {
    	
    }
    
    public Post(String profile, String desc) {
    	this.profile=profile;
    	this.desc=desc;
    }


    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String[] getTechs() {
        return techs;
    }

    public void setTechs(String[] techs) {
        this.techs = techs;
    }

    @Override
    public String toString() {
    	StringBuffer bf=new StringBuffer();
    	Arrays.stream(techs).forEach( (String s)-> bf.append(s+",") );
        return "Post{" +
                "profile='" + profile + '\'' +
                ", desc='" + desc + '\'' +
                ", exp=" + exp +
                //", techs=" + Arrays.toString(techs)+
                ", techs =" + bf.substring(0,bf.length()-1) +
                " }";
    }
}