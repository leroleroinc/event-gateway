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
public class NounService {

	@Autowired
	@Qualifier("nounsWebClient")
	private WebClient webClient;

	@CircuitBreaker(name = "nounservice", fallbackMethod = "hello")
	@Bulkhead(name = "bulkheadnounservice", fallbackMethod = "hello")
	@Retry(name = "retrynounservice", fallbackMethod = "hello")
	@RateLimiter(name = "ratelimiternounservice", fallbackMethod = "hello")
	public Flux<String> randomNounList(Integer size) {
		return webClient.get()
			.uri("/nouns?size=" + size)
			.retrieve()
			.bodyToFlux(String.class);
	}

	@CircuitBreaker(name = "nounservice", fallbackMethod = "hello")
	@Bulkhead(name = "bulkheadnounservice", fallbackMethod = "hello")
	@Retry(name = "retrynounservice", fallbackMethod = "hello")
	@RateLimiter(name = "ratelimiternounservice", fallbackMethod = "hello")
	public Flux<String> randomNounEvents(Integer interval) {
		return webClient.get()
			.uri("/nouns/events?interval=" + interval)
			.retrieve()
			.bodyToFlux(String.class);
	}

	public Flux<String> hello() {
		return Flux.just("Hello");
	}

}
