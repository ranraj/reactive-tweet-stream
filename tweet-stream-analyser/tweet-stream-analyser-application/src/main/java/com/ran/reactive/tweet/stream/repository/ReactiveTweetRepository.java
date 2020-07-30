package com.ran.reactive.tweet.stream.repository;

import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.ran.reactive.tweet.stream.model.Tweet;

import reactor.core.publisher.Flux;

public interface ReactiveTweetRepository extends ReactiveCrudRepository<Tweet, String> {

    @Tailable
    Flux<Tweet> findWithTailableCursorBy();

}
