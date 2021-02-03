package com.ran.reactive.tweet.stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.ran.reactive.tweet.stream.model.Tweet;
import com.ran.reactive.tweet.stream.nlp.SentimentAnalyzer;
import com.ran.reactive.tweet.stream.repository.ReactiveTweetRepository;

import reactor.core.publisher.Flux;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

@EnableReactiveMongoRepositories
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class TweetStreamApplication {

	private static Logger log = LoggerFactory.getLogger(TweetStreamApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TweetStreamApplication.class, args);
	}

	/**
	 * Rest API Router
	 * 
	 * @param reactiveTweetRepository
	 * @return
	 */
	@Bean
	RouterFunction<ServerResponse> routes(ReactiveTweetRepository reactiveTweetRepository) {
		return route(GET("/stream/tweets"),
				request -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
						.body(reactiveTweetRepository.findWithTailableCursorBy(), Tweet.class)).andRoute(GET("/tweets"),
								request -> ok().contentType(MediaType.APPLICATION_JSON)
										.body(reactiveTweetRepository.findAll(), Tweet.class));
	}

	@Autowired
	private SentimentAnalyzer sentimentAnalyzer;

	@Bean
	public CommandLineRunner tweetBot(Configuration twitterConfiguration,
			ReactiveTweetRepository tweetRepo) {

		return args -> {

			final String[] tracks = (args.length > 0) ? args : new String[] { "#reactive" };

			Flux<Tweet> tweetList = Flux.create(sink -> {
				TwitterStream twitterStream = new TwitterStreamFactory(twitterConfiguration).getInstance();
				twitterStream.onStatus( status -> {
					Tweet tweet = Tweet.fromStatus(status);					
					sink.next(tweet);	
				});
				twitterStream.onException(sink::error);
				twitterStream.filter(tracks);
				sink.onCancel(twitterStream::shutdown);
			});
			Flux<Tweet> tweets = tweetList.map(tweet -> {
				tweet.setSentiment(sentimentAnalyzer.getSentiment(tweet.getText()));
				return tweet;
			});
			tweetRepo
					.saveAll(tweets)					
					.subscribe(tweet -> log.info(tweet.toString()));

		};
	}

}
