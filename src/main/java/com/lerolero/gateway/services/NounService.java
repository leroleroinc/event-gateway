package com.lerolero.gateway.services;

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

//	@Autowired
//	private NounService(@Value("${webservice.nouns.baseurl}") String baseURL) {
//		this.webClient = WebClient.create(baseURL);
//	}

	public Flux<String> randomNounList(Integer size) {
		return webClient.get()
			.uri("/nouns?size=" + size)
			.retrieve()
			.bodyToFlux(String.class);
	}

	public Flux<String> randomNounEvents(Integer interval) {
		return webClient.get()
			.uri("/nouns/events?interval=" + interval)
			.retrieve()
			.bodyToFlux(String.class);
	}

}
