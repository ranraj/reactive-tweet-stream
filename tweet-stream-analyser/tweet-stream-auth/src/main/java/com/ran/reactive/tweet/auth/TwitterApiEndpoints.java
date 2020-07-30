package com.ran.reactive.tweet.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TwitterApiEndpoints {

	TWITTER_STATUSES_STREAM_API_URL(Constants.TWITTER_STATUSES_STREAM_API_URL);

	@Getter
	private final String value;

	private static class Constants {
		public static final String TWITTER_STATUSES_STREAM_API_URL = "https://stream.twitter.com/1.1/statuses/filter.json";
	}
}
