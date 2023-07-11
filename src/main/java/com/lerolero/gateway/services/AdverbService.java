package com.lerolero.gateway.services;

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

//	@Autowired
//	private AdverbService(@Value("${webservice.adverbs.baseurl}") String baseURL) {
//		this.webClient = WebClient.create(baseURL);
//	}

	public Flux<String> randomAdverbList(Integer size) {
		return webClient.get()
			.uri("/adverbs?size=" + size)
			.retrieve()
			.bodyToFlux(String.class);
	}

	public Flux<String> randomAdverbEvents(Integer interval) {
		return webClient.get()
			.uri("/adverbs/events?interval=" + interval)
			.retrieve()
			.bodyToFlux(String.class);
	}

}
