package com.ran.reactive.tweet.stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.ran.reactive.tweet.auth.TwitterOAuth;
import com.ran.reactive.tweet.auth.TwitterProperties;

@Configuration
public class TweetStreamConfiguration {
	
	@Value("${cors.allowed.url}")
	private String crossAllowedUrl;
		
	@Bean
	public TwitterProperties twitterProperties() {
		return new TwitterProperties();
	}

	@Bean
	TwitterOAuth twitterAuth(TwitterProperties twitterProperties) {
		return new TwitterOAuth(twitterProperties);
	}

	@Bean
	CorsWebFilter corsFilter() {

		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.addAllowedOrigin(getCrossAllowedUrl());		
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
	}

	public String getCrossAllowedUrl() {
		if(crossAllowedUrl != null) {
			return crossAllowedUrl.trim();
		}
		return crossAllowedUrl;
	}
	
	
}