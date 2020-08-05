package com.ran.reactive.tweet.stream;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.ran.reactive.tweet.auth.TwitterOAuth;
import com.ran.reactive.tweet.auth.TwitterProperties;

import twitter4j.conf.ConfigurationBuilder;
import twitter4j.util.TimeSpanConverter;


@Configuration
public class TweetStreamConfiguration {
	
	@Value("${cors.allowed.url}")
	private String crossAllowedUrl;
		
	@Bean
	public TwitterProperties twitterProperties() {
		return new TwitterProperties();
	}

	//TODO : Delete
	@Bean
	TwitterOAuth twitterAuth(TwitterProperties twitterProperties) {
		return new TwitterOAuth(twitterProperties);
	}
	
	/**
     * Configuration of Twitter4J including authentication details (OAuth tokens)
     */
    @Bean
    public twitter4j.conf.Configuration configuration(TwitterProperties properties) {
        return new ConfigurationBuilder()
            .setOAuthConsumerKey(properties.getConsumerKey())
            .setOAuthConsumerSecret(properties.getConsumerSecret())
            .setOAuthAccessToken(properties.getToken())
            .setOAuthAccessTokenSecret(properties.getSecret())
            .build();
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
	
	@Bean 
	Converter<Date, ZonedDateTime> converter() {
		return new ZonedDateTimeReadConverter();
	}
	
	public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {
	    @Override
	    public ZonedDateTime convert(Date date) {
	        return date.toInstant().atZone(ZoneOffset.UTC);
	    }
	}
}