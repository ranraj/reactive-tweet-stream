package com.ran.reactive.tweet.stream.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import twitter4j.Status;

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
    private Date createdAt;

    @JsonProperty("text")
    private String text;

    @JsonProperty("user")
    private User user;

    public String getId() {
        return id;
    }
    
    private Sentiment sentiment;

	public static Tweet fromStatus(Status status) {
		return new Tweet(String.valueOf(status.getId()),
				status.getCreatedAt(), status.getText(), User.fromTwitter4jUser(status.getUser()));
	}

	public Tweet(String id, Date createdAt, String text, User user) {
		this.id = id;
		this.createdAt = createdAt;
		this.text = text;
		this.user = user;
		
	}
   
     
}
