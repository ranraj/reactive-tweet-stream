package com.ran.reactive.tweet.stream;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

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

import com.ran.reactive.tweet.auth.TwitterApiEndpoints;
import com.ran.reactive.tweet.auth.TwitterOAuth;
import com.ran.reactive.tweet.stream.model.Tweet;
import com.ran.reactive.tweet.stream.nlp.SentimentAnalyzer;
import com.ran.reactive.tweet.stream.repository.ReactiveTweetRepository;

import reactor.core.publisher.Flux;

@EnableReactiveMongoRepositories
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class TweetStreamApplication{

	private static Logger log = LoggerFactory.getLogger(TweetStreamApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TweetStreamApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routes(ReactiveTweetRepository reactiveTweetRepository) {
		return route(GET("/tweets"), request -> ok().contentType(MediaType.TEXT_EVENT_STREAM)
				.body(reactiveTweetRepository.findWithTailableCursorBy(), Tweet.class));
	}

	@Autowired
	private SentimentAnalyzer sentimentAnalyzer;	 

	@Bean
	public CommandLineRunner tweetBot(TwitterOAuth twitterOAuth, ReactiveTweetRepository tweetRepo) {

		return args -> {
			MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

			String tracks = "#reactive";
			if (args.length > 0) {
				log.info("Using arguments as tracks");
				tracks = String.join(",", args);
			}

			log.info("Filtering tracks [{}]", tracks);
			body.add("track", tracks);

			ExchangeFilterFunction filter = (currentRequest, next) -> next.exchange(ClientRequest
					.from(currentRequest).header(HttpHeaders.AUTHORIZATION, twitterOAuth
							.oAuth1Header(currentRequest.url(), currentRequest.method(), body.toSingleValueMap()))
					.build());
			WebClient webClient = WebClient.builder().filter(filter).build();

			Flux<Tweet> tweets = webClient.post().uri(TwitterApiEndpoints.TWITTER_STATUSES_STREAM_API_URL.getValue())
					.contentType(MediaType.APPLICATION_FORM_URLENCODED).body(BodyInserters.fromFormData(body))
					.exchange().flatMapMany(clientResponse -> clientResponse.bodyToFlux(Tweet.class)).map(tweet -> {
						tweet.setSentiment(sentimentAnalyzer.getSentiment(tweet.getText()));
						return tweet;
					});

			tweetRepo.saveAll(tweets).subscribe(System.out::println);

		};
	}

}
