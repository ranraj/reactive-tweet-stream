package com.ran.reactive.tweet.stream.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class User {

	@JsonProperty("id")
	private long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("profile_image_url")
	private String profileImageUrl;

	@JsonProperty("profile_image_url_https")
	private String profileImageUrlHttps;

	@JsonProperty("screen_name")
	private String screenName;

	public static User fromTwitter4jUser(twitter4j.User twitter4jUser) {
		User user = new User();
		user.setId(twitter4jUser.getId());
		user.setName(twitter4jUser.getName());
		user.setScreenName(twitter4jUser.getScreenName());
		user.setProfileImageUrl(twitter4jUser.getBiggerProfileImageURL());
		user.setProfileImageUrlHttps(twitter4jUser.getBiggerProfileImageURLHttps());
		return user;
	}

}
