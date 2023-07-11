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
public class VerbService {

	@Autowired
	@Qualifier("verbsWebClient")
	private WebClient webClient;

	@CircuitBreaker(name = "verbservice", fallbackMethod = "hello")
	@Bulkhead(name = "bulkheadverbservice", fallbackMethod = "hello")
	@Retry(name = "retryverbservice", fallbackMethod = "hello")
	@RateLimiter(name = "ratelimiterverbservice", fallbackMethod = "hello")
	public Flux<String> randomVerbList(Integer size) {
		return webClient.get()
			.uri("/verbs?size=" + size)
			.retrieve()
			.bodyToFlux(String.class);
	}

	@CircuitBreaker(name = "verbservice", fallbackMethod = "hello")
	@Bulkhead(name = "bulkheadverbservice", fallbackMethod = "hello")
	@Retry(name = "retryverbservice", fallbackMethod = "hello")
	@RateLimiter(name = "ratelimiterverbservice", fallbackMethod = "hello")
	public Flux<String> randomVerbEvents(Integer interval) {
		return webClient.get()
			.uri("/verbs/events?interval=" + interval)
			.retrieve()
			.bodyToFlux(String.class);
	}

	public Flux<String> hello() {
		return Flux.just("Hello");
	}

}
