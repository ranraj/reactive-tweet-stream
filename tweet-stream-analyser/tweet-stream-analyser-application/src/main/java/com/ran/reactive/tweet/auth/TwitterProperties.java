package com.ran.reactive.tweet.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ConfigurationProperties(prefix = "twitter")
public class TwitterProperties {

    /**
     * Consumer Key (API Key)
     */
    private String consumerKey;

    /**
     * Consumer Secret (API Secret)
     */
    private String consumerSecret;

    /**
     * Access Token
     */
    private String token;

    /**
     * Access Token Secret
     */
    private String secret;
   
}