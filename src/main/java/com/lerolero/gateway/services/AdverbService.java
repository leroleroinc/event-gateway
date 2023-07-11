package com.lerolero.gateway.services;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@Service
public class AdverbService {

	@Autowired
	@Qualifier("adverbsWebClient")
	private WebClient webClient;

	@CircuitBreaker(name = "adverbservice", fallbackMethod = "hello")
	@Bulkhead(name = "bulkheadadverbservice", fallbackMethod = "hello")
	@Retry(name = "retryadverbservice", fallbackMethod = "hello")
	@RateLimiter(name = "ratelimiteradverbservice", fallbackMethod = "hello")
	public Flux<String> randomAdverbList(Integer size) {
		return webClient.get()
			.uri("/adverbs?size=" + size)
			.retrieve()
			.bodyToFlux(String.class);
	}

	@CircuitBreaker(name = "adverbservice", fallbackMethod = "hello")
	@Bulkhead(name = "bulkheadadverbservice", fallbackMethod = "hello")
	@Retry(name = "retryadverbservice", fallbackMethod = "hello")
	@RateLimiter(name = "ratelimiteradverbservice", fallbackMethod = "hello")
	public Flux<String> randomAdverbEvents(Integer interval) {
		return webClient.get()
			.uri("/adverbs/events?interval=" + interval)
			.retrieve()
			.bodyToFlux(String.class);
	}

	public Flux<String> hello() {
		return Flux.just("Hello");
	}

}
