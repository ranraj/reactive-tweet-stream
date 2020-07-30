package com.ran.reactive.tweet.stream.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "tweets")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Tweet {

    @Id
    @JsonProperty("id_str")
    private String id;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("text")
    private String text;

    @JsonProperty("user")
    private User user;

    public String getId() {
        return id;
    }
    
    private Sentiment sentiment;
   
     
}
