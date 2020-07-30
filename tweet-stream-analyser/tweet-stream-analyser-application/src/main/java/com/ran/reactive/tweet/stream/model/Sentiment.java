package com.ran.reactive.tweet.stream.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class Sentiment {
	
	private double score;
	@JsonProperty("sentiment_type")
	private String type;

}
